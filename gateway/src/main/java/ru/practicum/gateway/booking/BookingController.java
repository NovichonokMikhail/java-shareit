package ru.practicum.gateway.booking;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.dto.booking.BookingDtoCreation;

import static ru.practicum.common.util.Constants.headerName;

@RestController
@RequestMapping(path = "/bookings")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    BookingClient bookingClient;

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> get(@PathVariable Long bookingId, @RequestHeader(headerName) Long userId) {
        log.info("GET /bookings/{}; X-Sharer-User-Id={}", bookingId, userId);
        return bookingClient.getById(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllByBookerAndState(
            @RequestParam(required = false, defaultValue = "ALL") String state,
            @RequestHeader(headerName) Long bookerId) {
        log.info("GET /bookings?state={}; X-Sharer-User-Id={}", state, bookerId);
        return bookingClient.getAllByBookerAndState(state, bookerId);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllByOwnerAndState(
            @RequestParam(required = false, defaultValue = "ALL") String state,
            @RequestHeader(headerName) Long ownerId) {
        log.info("GET /bookings/owner?state={}; X-Sharer-User-Id={}", state, ownerId);
        return bookingClient.getAllByOwnerAndState(state, ownerId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody BookingDtoCreation dto,
                                     @RequestHeader(headerName) Long bookerId) {
        log.info("POST /bookings; X-Sharer-User-Id={}", bookerId);
        return bookingClient.createBooking(bookerId, dto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> judge(@PathVariable Long bookingId, @RequestHeader(headerName) Long ownerId,
                                    @RequestParam Boolean approved) {
        log.info("PATCH /bookings/{}?approved={}; X-Sharer-User-Id={}", bookingId, approved, ownerId);
        return bookingClient.judge(bookingId, ownerId, approved);
    }
}