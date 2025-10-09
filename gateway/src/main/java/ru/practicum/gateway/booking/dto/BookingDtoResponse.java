package ru.practicum.gateway.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.gateway.booking.model.BookingStatus;
import ru.practicum.gateway.item.dto.ItemDto;
import ru.practicum.gateway.user.dto.UserDto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookingDtoResponse {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    BookingStatus status;
    UserDto booker;
    ItemDto item;
}