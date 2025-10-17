package ru.practicum.server.booking.mapper;

import ru.practicum.common.dto.booking.BookingDtoCreation;
import ru.practicum.common.dto.booking.BookingDtoResponse;
import ru.practicum.server.booking.model.Booking;
import ru.practicum.common.dto.booking.BookingStatus;
import ru.practicum.common.dto.item.ItemDto;
import ru.practicum.server.item.mapper.ItemMapper;
import ru.practicum.server.item.model.Item;
import ru.practicum.common.dto.user.UserDto;
import ru.practicum.server.user.mapper.UserMapper;
import ru.practicum.server.user.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


public class BookingMapper {
    public static BookingDtoResponse bookingToDto(Booking obj) {
        if (obj == null)
            return null;
        final UserDto bookerDto = UserMapper.userToDto(obj.getBooker());
        final ItemDto itemDto = ItemMapper.itemToDto(obj.getItem());
        return new BookingDtoResponse(obj.getId(), utcToLocal(obj.getStart()), utcToLocal(obj.getEnd()),
                obj.getStatus(), bookerDto, itemDto);
    }

    public static Booking dtoToNewBooking(BookingDtoCreation dto, Item item, User booker) {
        return new Booking(null, item, booker, localToUtc(dto.getStart()),
                localToUtc(dto.getEnd()), BookingStatus.WAITING);
    }

    public static LocalDateTime utcToLocal(ZonedDateTime zonedTime) {
        return zonedTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static ZonedDateTime localToUtc(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
    }

    public static ZonedDateTime getUtcNow() {
        return localToUtc(LocalDateTime.now());
    }
}