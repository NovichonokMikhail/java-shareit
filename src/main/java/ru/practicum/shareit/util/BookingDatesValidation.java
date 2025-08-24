package ru.practicum.shareit.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BookingDatesValidator.class)
@Documented
public @interface BookingDatesValidation {
    String message() default "Booking date is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}