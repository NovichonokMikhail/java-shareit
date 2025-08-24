package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.util.BookingDatesValidation;

import java.time.LocalDateTime;

@BookingDatesValidation
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class BookingDtoCreation {
    @FutureOrPresent
    @NotNull(message = "start date may not be null")
    LocalDateTime start;
    @Future
    @NotNull(message = "end date may not be null")
    LocalDateTime end;
    BookingStatus status;
    Long bookerId;
    @NotNull(message = "item id must be present")
    Long itemId;
}