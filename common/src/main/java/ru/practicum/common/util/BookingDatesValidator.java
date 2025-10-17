package ru.practicum.common.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.common.dto.booking.BookingDtoCreation;

public class BookingDatesValidator implements ConstraintValidator<BookingDatesValidation, BookingDtoCreation> {
    @Override
    public boolean isValid(BookingDtoCreation dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getStart().isBefore(dto.getEnd());
    }
}