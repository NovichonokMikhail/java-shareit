package ru.practicum.server.item.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.booking.BookingStatus;
import ru.practicum.common.dto.item.CommentDto;
import ru.practicum.common.dto.item.ItemDto;
import ru.practicum.common.dto.item.ItemDtoExtended;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.exception.ValidationException;
import ru.practicum.server.booking.model.Booking;
import ru.practicum.server.booking.repository.BookingRepository;
import ru.practicum.server.item.mapper.CommentMapper;
import ru.practicum.server.item.mapper.ItemMapper;
import ru.practicum.server.item.model.Comment;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.item.repository.CommentRepository;
import ru.practicum.server.item.repository.ItemRepository;
import ru.practicum.server.request.repository.ItemRequestRepository;
import ru.practicum.server.user.model.User;
import ru.practicum.server.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.server.request.service.ItemRequestServiceImpl.REQUEST_NOT_FOUND;
import static ru.practicum.server.user.service.UserServiceImpl.USER_NOT_FOUND;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    // Common exceptions
    public static final NotFoundException ITEM_NOT_FOUND = new NotFoundException("Item with this id does not exist");
    // Storages
    ItemRepository itemRepository;
    UserRepository userRepository;
    CommentRepository commentRepository;
    BookingRepository bookingRepository;
    ItemRequestRepository requestRepository;

    @Override
    public ItemDto create(ItemDto dto, Long ownerId) {
        // Checking existence of a user
        final User owner = userRepository.findById(ownerId).orElseThrow(() -> USER_NOT_FOUND);
        // Saving new item
        if (dto.hasRequestId() && !requestRepository.existsById(dto.getRequestId()))
            throw REQUEST_NOT_FOUND;
        final Item createdItem = itemRepository.save(ItemMapper.dtoToNewItem(dto, owner));
        return ItemMapper.itemToDto(createdItem);
    }

    @Override
    public CommentDto createComment(CommentDto dto, Long itemId, Long authorId) {
        // Validate existence of author and item
        final User author = userRepository.findById(authorId).orElseThrow(() -> USER_NOT_FOUND);
        final Item item = itemRepository.findById(itemId).orElseThrow(() -> ITEM_NOT_FOUND);
        if (authorId.equals(item.getOwner().getId()))
            throw new ValidationException("Owner cannot post comments under their item");
        // Check if comment author previously booked the item
        List<Booking> previousBookings = bookingRepository
                .findAllByBookerIdAndItemIdAndStatusIs(authorId, itemId, BookingStatus.APPROVED);
        LocalDateTime now = LocalDateTime.now();
        boolean bookingIsOver = previousBookings.stream()
                .map(Booking::getEnd)
                .allMatch(end -> end.isBefore(now));
        // Throw errors
        if (previousBookings.isEmpty())
            throw new ValidationException("Author never booked the item");
        if (!bookingIsOver)
            throw new ValidationException("Booking was not yet finished");
        final Comment comment = commentRepository.save(CommentMapper.dtoToComment(dto, author, item));
        return CommentMapper.commentToDto(comment);
    }

    @Override
    public ItemDto remove(Long id) {
        // Checking item existence
        final Item item = itemRepository.findById(id).orElseThrow(() -> ITEM_NOT_FOUND);
        itemRepository.deleteById(id);
        return ItemMapper.itemToDto(item);
    }

    @Override
    public ItemDto modify(final ItemDto itemDto, final long ownerId) {
        // Checking owner and item existence
        final Item origin = itemRepository.findById(itemDto.getId()).orElseThrow(() -> ITEM_NOT_FOUND);
        // Validating ownership of an item
        if (origin.getOwner().getId() != ownerId)
            throw new NotFoundException("This user cannot access this item");
        // Updating and returning in proper form
        final Item updItem = itemRepository.save(ItemMapper.dtoToItem(itemDto, origin));
        return ItemMapper.itemToDto(updItem);
    }

    @Override
    public Collection<ItemDto> find(final String text) {
        return itemRepository.findAllAvailableByText(text)
                .stream()
                .map(ItemMapper::itemToDto)
                .toList();
    }

    @Override
    public ItemDtoExtended find(Long itemId, long userId) {
        // Checking existence and throwing exception otherwise
        final Item item = itemRepository.findById(itemId).orElseThrow(() -> ITEM_NOT_FOUND);
        Booking nextBooking = null;
        Booking lastBooking = null;
        // If owner then fill data, else use null
        if (item.getOwner().getId() == userId) {
//            final ZonedDateTime now = getUtcNow();
            final LocalDateTime now = LocalDateTime.now();
            lastBooking = bookingRepository
                    .findTop1ByItemIdAndEndBeforeOrderByStartDesc(itemId, now)
                    .orElse(null);
            nextBooking = bookingRepository
                    .findTop1ByItemIdAndStartAfterOrderByStartAsc(itemId, now)
                    .orElse(null);
        }
        return ItemMapper.itemToExtendedDto(item, commentRepository.findAllByItemId(item.getId()),
                lastBooking, nextBooking);
    }

    @Override
    public Collection<ItemDtoExtended> findAllUserItems(Long ownerId) {
        // Validate user existence
        userRepository.findById(ownerId).orElseThrow(() -> USER_NOT_FOUND);
//        final ZonedDateTime now = getUtcNow();
        final LocalDateTime now = LocalDateTime.now();
        // Get answer and map to extended dto
        return itemRepository.findAllByOwnerId(ownerId).stream()
                .map(item -> {
                    final Long itemId = item.getId();
                    Booking lastBooking = bookingRepository
                            .findTop1ByItemIdAndEndBeforeOrderByStartDesc(itemId, now)
                            .orElse(null);
                    Booking nextBooking = bookingRepository
                            .findTop1ByItemIdAndStartAfterOrderByStartAsc(itemId, now)
                            .orElse(null);
                    return ItemMapper.itemToExtendedDto(item,
                            commentRepository.findAllByItemId(itemId),
                            lastBooking,
                            nextBooking);
                })
                .toList();
    }
}