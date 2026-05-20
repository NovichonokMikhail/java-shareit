package ru.practicum.common.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.common.dto.booking.BookingDtoCreation;

import java.time.Instant;
import java.time.LocalDateTime;

import static ru.practicum.common.util.TimeConverter.instantToLocal;

public class BookingDatesValidator implements ConstraintValidator<BookingDatesValidation, BookingDtoCreation> {
    @Override
    public boolean isValid(BookingDtoCreation dto, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime now = instantToLocal(Instant.now());
        boolean timeframeIsValid = dto.getStart().isBefore(dto.getEnd());
        boolean startIsInPresentOrFuture = dto.getStart().isAfter(now) || dto.getStart().isEqual(now);
        boolean endIsInFuture = dto.getEnd().isAfter(now);
        return timeframeIsValid && startIsInPresentOrFuture && endIsInFuture;
    }
}