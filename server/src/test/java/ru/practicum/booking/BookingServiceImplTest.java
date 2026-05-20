package ru.practicum.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.common.dto.booking.BookingDtoCreation;
import ru.practicum.common.dto.booking.BookingDtoResponse;
import ru.practicum.common.dto.booking.BookingState;
import ru.practicum.common.dto.booking.BookingStatus;
import ru.practicum.server.booking.mapper.BookingMapper;
import ru.practicum.server.booking.model.Booking;
import ru.practicum.server.booking.repository.BookingRepository;
import ru.practicum.server.booking.service.BookingServiceImpl;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.item.repository.ItemRepository;
import ru.practicum.server.user.model.User;
import ru.practicum.server.user.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static ru.practicum.common.util.TimeConverter.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {
    @Mock
    BookingRepository bookingRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    BookingServiceImpl bookingService;

    final Instant start = localToInstant(LocalDateTime.now());
    final Instant end = localToInstant(LocalDateTime.now().plusHours(1));
    final User user = new User(1L, null, null);
    final Item item = new Item(1L, user, null, null, null, true);
    Booking booking = new Booking(1L, item, user, start, end, BookingStatus.WAITING);
    BookingDtoCreation dtoCreation = new BookingDtoCreation(instantToLocal(start),
            instantToLocal(end), BookingStatus.WAITING, 1L, 1L);
    BookingDtoResponse response = BookingMapper.bookingToDto(booking);

    @Test
    void canCreate() {
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));
        Mockito.when(bookingRepository.save(ArgumentMatchers.any(Booking.class)))
                .thenReturn(booking);

        BookingDtoResponse savedBooking = bookingService.create(dtoCreation, 1L);
        assertThat(savedBooking, equalTo(response));
    }

    @Test
    void canJudge() {
        Booking updatedBooking = booking.toBuilder().status(BookingStatus.APPROVED).build();
        Mockito.when(bookingRepository.save(ArgumentMatchers.any(Booking.class)))
                .thenReturn(updatedBooking);
        Mockito.when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.of(booking));
        Mockito.when(userRepository.existsById(anyLong()))
                .thenReturn(true);

        BookingDtoResponse judgedBooking = bookingService.judge(1L, 1L, BookingStatus.APPROVED);
        assertThat(judgedBooking.getStatus(), equalTo(BookingStatus.APPROVED));
        assertThat(judgedBooking, equalTo(BookingMapper.bookingToDto(updatedBooking)));
    }

    @Test
    void canGetById() {
        Mockito.when(userRepository.existsById(anyLong()))
                .thenReturn(true);
        Mockito.when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.of(booking));
        BookingDtoResponse savedBooking = bookingService.get(1L, 1L);
        assertThat(savedBooking, equalTo(response));
    }

    @Test
    void canGetAllByBookerAndState() {
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        Mockito.when(bookingRepository.findAllByBookerIdOrderByStartDesc(anyLong()))
                .thenReturn(List.of(booking, booking));
        Collection<BookingDtoResponse> bookings = bookingService.getAllByBookerAndState(1L, BookingState.ALL);
        assertThat(bookings.size(), equalTo(2));
        assertThat(new ArrayList<>(bookings).getFirst(), equalTo(response));
    }

    @Test
    void canGetAllByOwnerAndState() {
        Mockito.when(userRepository.existsById(anyLong()))
                .thenReturn(true);
        Mockito.when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong()))
                .thenReturn(List.of(booking, booking));
        Collection<BookingDtoResponse> bookings = bookingService.getAllByOwnerAndState(1L, BookingState.ALL);
        assertThat(bookings.size(), equalTo(2));
        assertThat(new ArrayList<>(bookings).getFirst(), equalTo(response));
    }
}