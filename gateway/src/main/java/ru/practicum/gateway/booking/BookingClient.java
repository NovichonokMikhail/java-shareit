package ru.practicum.gateway.booking;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.common.dto.booking.BookingDtoCreation;
import ru.practicum.gateway.client.BaseClient;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    public BookingClient(RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/bookings"))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> getById(long bookingId, long userId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> getAllByOwnerAndState(String state, long ownerId) {
        return get("", ownerId, Map.of("state", state));
    }

    public ResponseEntity<Object> getAllByBookerAndState(String state, long bookerId) {
        return get("", bookerId, Map.of("state", state));
    }

    public ResponseEntity<Object> createBooking(long bookerId, BookingDtoCreation dto) {
        return post("", bookerId, dto);
    }

    public ResponseEntity<Object> judge(long bookingId, long ownerId, Boolean approved) {
        return patch(String.format("/%s?approved=%s", bookingId, approved), ownerId);
    }
}