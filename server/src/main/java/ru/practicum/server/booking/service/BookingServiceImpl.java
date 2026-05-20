package ru.practicum.server.booking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.booking.BookingDtoCreation;
import ru.practicum.common.dto.booking.BookingDtoResponse;
import ru.practicum.common.dto.booking.BookingState;
import ru.practicum.common.dto.booking.BookingStatus;
import ru.practicum.common.exception.AccessDeniedException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.exception.ValidationException;
import ru.practicum.server.booking.mapper.BookingMapper;
import ru.practicum.server.booking.model.Booking;
import ru.practicum.server.booking.repository.BookingRepository;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.item.repository.ItemRepository;
import ru.practicum.server.user.model.User;
import ru.practicum.server.user.repository.UserRepository;
import ru.practicum.server.user.service.UserServiceImpl;

import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

import static ru.practicum.server.item.service.ItemServiceImpl.ITEM_NOT_FOUND;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
    public static final NotFoundException BOOKING_NOT_FOUND = new NotFoundException("Booking does not exist");
    public static final ValidationException ITEM_UNAVAILABLE = new ValidationException("Item is unavailable");
    public static final AccessDeniedException ACCESS_DENIED =
            new AccessDeniedException("This user doesn't have access to information");
    // Repositories
    UserRepository userRepository;
    ItemRepository itemRepository;
    BookingRepository bookingRepository;

    @Override
    public BookingDtoResponse create(BookingDtoCreation dto, Long bookerId) {
        final User booker = userRepository
                .findById(bookerId)
                .orElseThrow(() -> UserServiceImpl.USER_NOT_FOUND);
        final Item item = itemRepository
                .findById(dto.getItemId())
                .orElseThrow(() -> ITEM_NOT_FOUND);
        if (!item.getAvailable())
            throw ITEM_UNAVAILABLE;
        final Booking booking = bookingRepository.save(BookingMapper.dtoToNewBooking(dto, item, booker));
        log.info("Booking with id={} created", booking.getId());
        return BookingMapper.bookingToDto(booking);
    }

    @Override
    public BookingDtoResponse judge(Long bookingId, Long ownerId, BookingStatus status) {
        final Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> BOOKING_NOT_FOUND);
        if (!ownerId.equals(booking.getItem().getOwner().getId()))
            throw ACCESS_DENIED;
        if (!userRepository.existsById(ownerId))
            throw UserServiceImpl.USER_NOT_FOUND;
        if (List.of(BookingStatus.APPROVED, BookingStatus.REJECTED).contains(booking.getStatus()))
            throw new ValidationException("Status for this booking cannot be updated");
        Booking updatedBooking = booking.toBuilder()
                .status(status)
                .build();
        return BookingMapper.bookingToDto(bookingRepository.save(updatedBooking));
    }

    @Override
    public BookingDtoResponse get(Long bookingId, Long userId) {
        if (!userRepository.existsById(userId))
            throw UserServiceImpl.USER_NOT_FOUND;
        final Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> BOOKING_NOT_FOUND);
        if (!userId.equals(booking.getItem().getOwner().getId()) && !userId.equals(booking.getBooker().getId()))
            throw ACCESS_DENIED;
        return BookingMapper.bookingToDto(booking);
    }

    @Override
    public List<BookingDtoResponse> getAllByBookerAndState(Long bookerId, BookingState state) {
        if (userRepository.findById(bookerId).isEmpty())
            throw UserServiceImpl.USER_NOT_FOUND;
        List<Booking> list = bookingRepository.findAllByBookerIdOrderByStartDesc(bookerId);
        return list
                .stream()
                .filter(getFilterByState(state))
                .map(BookingMapper::bookingToDto)
                .toList();
    }

    @Override
    public List<BookingDtoResponse> getAllByOwnerAndState(Long ownerId, BookingState state) {
        if (!userRepository.existsById(ownerId))
            throw UserServiceImpl.USER_NOT_FOUND;
        List<Booking> list = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(ownerId);
        return list
                .stream()
                .filter(getFilterByState(state))
                .map(BookingMapper::bookingToDto)
                .toList();
    }

    public static Predicate<Booking> getFilterByState(BookingState state) {
        final Instant now = Instant.now();
        return switch (state) {
            case ALL -> b -> true;
            case CURRENT -> b -> (b.getStart().isBefore(now) &&
                    b.getEnd().isAfter(now) &&
                    b.getStatus() == BookingStatus.APPROVED);
            case PAST -> b -> b.getEnd().isBefore(now) && b.getStatus() == BookingStatus.APPROVED;
            case FUTURE -> b -> b.getStart().isAfter(now) && b.getStatus() == BookingStatus.APPROVED;
            case WAITING -> b -> b.getStatus() == BookingStatus.WAITING;
            case REJECTED -> b -> b.getStatus() == BookingStatus.REJECTED;
        };
    }
}