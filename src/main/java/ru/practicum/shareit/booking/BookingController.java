package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoCreation;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final String headerName = "X-Sharer-User-Id";

    @GetMapping("/{bookingId}")
    public BookingDtoResponse get(@PathVariable Long bookingId, @RequestHeader(headerName) Long userId) {
        log.info("GET /bookings/{}; X-Sharer-User-Id={}", bookingId, userId);
        return bookingService.get(bookingId, userId);
    }

    @GetMapping
    public Collection<BookingDtoResponse> getAllByBookerAndState(
            @RequestParam(required = false, defaultValue = "ALL") String state,
            @RequestHeader(headerName) Long bookerId) {
        log.info("GET /bookings?state={}; X-Sharer-User-Id={}", state, bookerId);
        return bookingService.getAllByBookerAndState(bookerId, BookingState.valueOf(state));
    }

    @GetMapping("/owner")
    public Collection<BookingDtoResponse> getAllByOwnerAndState(
            @RequestParam(required = false, defaultValue = "ALL") String state,
            @RequestHeader(headerName) Long ownerId) {
        log.info("GET /bookings/owner?state={}; X-Sharer-User-Id={}", state, ownerId);
        return bookingService.getAllByOwnerAndState(ownerId, BookingState.valueOf(state));
    }

    @PostMapping
    public BookingDtoResponse create(@Valid @RequestBody BookingDtoCreation dto,
                                     @RequestHeader(headerName) Long bookerId) {
        log.info("POST /bookings; X-Sharer-User-Id={}", bookerId);
        return bookingService.create(dto, bookerId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse judge(@PathVariable Long bookingId, @RequestHeader(headerName) Long ownerId,
                                    @RequestParam Boolean approved) {
        log.info("PATCH /bookings/{}?approved={}; X-Sharer-User-Id={}", bookingId, ownerId, approved);
        return bookingService.judge(bookingId, ownerId, approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
    }
}