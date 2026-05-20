package ru.practicum.common.dto.item;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.common.dto.booking.BookingDtoResponse;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ItemDtoExtended {
    Long id;
    String name;
    String description;
    Boolean available;
    @EqualsAndHashCode.Exclude
    List<CommentDto> comments;
    BookingDtoResponse lastBooking;
    BookingDtoResponse nextBooking;
}