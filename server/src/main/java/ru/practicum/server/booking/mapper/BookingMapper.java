package ru.practicum.server.booking.mapper;

import ru.practicum.common.dto.booking.BookingDtoCreation;
import ru.practicum.common.dto.booking.BookingDtoResponse;
import ru.practicum.common.dto.booking.BookingStatus;
import ru.practicum.common.dto.item.ItemDto;
import ru.practicum.common.dto.user.UserDto;
import ru.practicum.server.booking.model.Booking;
import ru.practicum.server.item.mapper.ItemMapper;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.user.mapper.UserMapper;
import ru.practicum.server.user.model.User;


public class BookingMapper {
    public static BookingDtoResponse bookingToDto(Booking obj) {
        if (obj == null)
            return null;
        final UserDto bookerDto = UserMapper.userToDto(obj.getBooker());
        final ItemDto itemDto = ItemMapper.itemToDto(obj.getItem());
        return new BookingDtoResponse(obj.getId(), obj.getStart(), obj.getEnd(),
                obj.getStatus(), bookerDto, itemDto);
    }

    public static Booking dtoToNewBooking(BookingDtoCreation dto, Item item, User booker) {
        return new Booking(null, item, booker, dto.getStart(),
                dto.getEnd(), BookingStatus.WAITING);
    }
}