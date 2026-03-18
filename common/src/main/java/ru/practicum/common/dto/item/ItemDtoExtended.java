package ru.practicum.common.dto.item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.common.dto.booking.BookingDtoResponse;

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