package ru.practicum.common.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.common.dto.item.ItemDto;
import ru.practicum.common.dto.user.UserDto;

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