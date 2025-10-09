package ru.practicum.gateway.item.mapper;

import ru.practicum.gateway.booking.mapper.BookingMapper;
import ru.practicum.gateway.booking.model.Booking;
import ru.practicum.gateway.item.dto.ItemDto;
import ru.practicum.gateway.item.dto.ItemDtoExtended;
import ru.practicum.gateway.item.model.Comment;
import ru.practicum.gateway.item.model.Item;
import ru.practicum.gateway.user.model.User;

import java.util.List;

public class ItemMapper {
    public static Item dtoToItem(final ItemDto dto, final Item origin) {
        return origin.toBuilder()
                .description(dto.hasDescription() ? dto.getDescription() : origin.getDescription())
                .name(dto.hasName() ? dto.getName() : origin.getName())
                .requestId(dto.getRequestId())
                .available(dto.hasAvailable() ? dto.getAvailable() : origin.getAvailable())
                .build();
    }

    public static Item dtoToNewItem(final ItemDto dto, final User owner) {
        return new Item(null, owner, dto.getName(), dto.getRequestId(), dto.getDescription(), dto.getAvailable());
    }

    public static ItemDtoExtended itemToExtendedDto(final Item item,
                                                    List<Comment> comments,
                                                    Booking lastBooking,
                                                    Booking nextBooking) {
        return new ItemDtoExtended(item.getId(), item.getName(), item.getDescription(), item.getAvailable(),
                CommentMapper.commentToDto(comments),
                BookingMapper.bookingToDto(lastBooking),
                BookingMapper.bookingToDto(nextBooking));
    }

    public static ItemDto itemToDto(final Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable(), item.getRequestId());
    }
}