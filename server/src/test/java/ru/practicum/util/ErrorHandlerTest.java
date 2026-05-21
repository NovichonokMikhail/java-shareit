package ru.practicum.util;

import org.junit.jupiter.api.Test;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.exception.ValidationException;
import ru.practicum.common.util.ErrorResponse;
import ru.practicum.server.util.ErrorHandler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ErrorHandlerTest {
    final ErrorHandler handler = new ErrorHandler();

    @Test
    void handleNotFound() {
        // set up
        final String message = "Something was not found";
        final ErrorResponse response = new ErrorResponse(message, "NotFoundException");
        final NotFoundException exception = new NotFoundException(message);
        // Testing
        ErrorResponse handledResponse = handler.handleNotFound(exception);
        assertThat(handledResponse.error(), equalTo(response.error()));
        assertThat(handledResponse.description(), equalTo(response.description()));
    }

    @Test
    void handleValidation() {
        // set up
        final String message = "invalid";
        final ErrorResponse response = new ErrorResponse(message, ValidationException.class.getName());
        final ValidationException exception = new ValidationException(message);
        // Testing
        ErrorResponse handledResponse = handler.handleValidation(exception);
        assertThat(handledResponse.error(), equalTo(response.error()));
        assertThat(handledResponse.description(), equalTo(response.description()));
    }

    @Test
    void handleOtherExceptions() {
        // set up
        final ErrorResponse responseRuntime = new ErrorResponse("runtime", RuntimeException.class.getName());
        final RuntimeException exceptionRuntime = new RuntimeException("runtime");
        final ErrorResponse responseDiv = new ErrorResponse("oops, division by 0", ArithmeticException.class.getName());
        final ArithmeticException exceptionDiv = new ArithmeticException("oops, division by 0");
        // Testing case 1
        ErrorResponse handledResponseRuntime = handler.handleOtherExceptions(exceptionRuntime);
        assertThat(handledResponseRuntime.error(), equalTo(responseRuntime.error()));
        assertThat(handledResponseRuntime.description(), equalTo(responseRuntime.description()));
        // Testing case 2
        ErrorResponse handledResponseDiv = handler.handleOtherExceptions(exceptionDiv);
        assertThat(handledResponseDiv.error(), equalTo(responseDiv.error()));
        assertThat(handledResponseDiv.description(), equalTo(responseDiv.description()));
    }
}