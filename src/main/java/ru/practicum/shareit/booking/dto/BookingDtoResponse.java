package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

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