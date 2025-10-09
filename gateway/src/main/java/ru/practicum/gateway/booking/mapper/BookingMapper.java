package ru.practicum.gateway.booking.mapper;

import ru.practicum.gateway.booking.dto.BookingDtoCreation;
import ru.practicum.gateway.booking.dto.BookingDtoResponse;
import ru.practicum.gateway.booking.model.Booking;
import ru.practicum.gateway.booking.model.BookingStatus;
import ru.practicum.gateway.item.dto.ItemDto;
import ru.practicum.gateway.item.mapper.ItemMapper;
import ru.practicum.gateway.item.model.Item;
import ru.practicum.gateway.user.dto.UserDto;
import ru.practicum.gateway.user.mapper.UserMapper;
import ru.practicum.gateway.user.model.User;

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