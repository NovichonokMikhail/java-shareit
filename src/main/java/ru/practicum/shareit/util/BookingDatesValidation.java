package ru.practicum.shareit.util;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BookingDatesValidator.class)
@Documented
public @interface BookingDatesValidation {
    String message() default "Booking date is invalid";
}