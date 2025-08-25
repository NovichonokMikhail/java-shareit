package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class ItemDtoExtended {
    Long id;
    String name;
    String description;
    Boolean available;
    List<CommentDto> comments;
    BookingDtoResponse lastBooking;
    BookingDtoResponse nextBooking;
}