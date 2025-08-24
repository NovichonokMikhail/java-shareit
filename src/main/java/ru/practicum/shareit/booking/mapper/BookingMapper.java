package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDtoCreation;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BookingMapper {
    public static final ZoneId GMT = ZoneId.of("GMT");

    public static BookingDtoResponse bookingToDto(Booking obj) {
        if (obj == null)
            return null;
        final UserDto bookerDto = UserMapper.userToDto(obj.getBooker());
        final ItemDto itemDto = ItemMapper.itemToDto(obj.getItem());
        return new BookingDtoResponse(obj.getId(), gmtToLocal(obj.getStart()), gmtToLocal(obj.getEnd()),
                obj.getStatus(), bookerDto, itemDto);
    }

    public static Booking dtoToNewBooking(BookingDtoCreation dto, Item item, User booker) {
        return new Booking(null, item, booker, localToGmt(dto.getStart()),
                localToGmt(dto.getEnd()), BookingStatus.WAITING);
    }

    public static LocalDateTime gmtToLocal(ZonedDateTime zonedTime) {
        return zonedTime.toLocalDateTime();
    }

    public static ZonedDateTime localToGmt(LocalDateTime localTime) {
        return ZonedDateTime.of(localTime, GMT);
    }
}