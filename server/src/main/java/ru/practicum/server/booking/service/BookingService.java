package ru.practicum.server.booking.service;

import ru.practicum.common.dto.booking.BookingDtoCreation;
import ru.practicum.common.dto.booking.BookingDtoResponse;
import ru.practicum.common.dto.booking.BookingState;
import ru.practicum.common.dto.booking.BookingStatus;

import java.util.List;

public interface BookingService {
    BookingDtoResponse create(BookingDtoCreation booking, Long bookerId);

    BookingDtoResponse judge(Long bookingId, Long ownerId, BookingStatus status);

    BookingDtoResponse get(Long bookingId, Long userId);

    List<BookingDtoResponse> getAllByBookerAndState(Long bookerId, BookingState state);

    List<BookingDtoResponse> getAllByOwnerAndState(Long ownerId, BookingState state);
}