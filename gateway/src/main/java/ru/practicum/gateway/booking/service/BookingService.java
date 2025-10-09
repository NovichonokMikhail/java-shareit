package ru.practicum.gateway.booking.service;

import ru.practicum.gateway.booking.dto.BookingDtoCreation;
import ru.practicum.gateway.booking.dto.BookingDtoResponse;
import ru.practicum.gateway.booking.dto.BookingState;
import ru.practicum.gateway.booking.model.BookingStatus;

import java.util.List;

public interface BookingService {
    BookingDtoResponse create(BookingDtoCreation booking, Long bookerId);

    BookingDtoResponse judge(Long bookingId, Long ownerId, BookingStatus status);

    BookingDtoResponse get(Long bookingId, Long userId);

    List<BookingDtoResponse> getAllByBookerAndState(Long bookerId, BookingState state);

    List<BookingDtoResponse> getAllByOwnerAndState(Long ownerId, BookingState state);
}