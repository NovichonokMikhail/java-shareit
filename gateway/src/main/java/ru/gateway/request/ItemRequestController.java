package ru.gateway.request;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.server.request.model.ItemRequest;

@RestController
@RequestMapping(path = "/requests")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemRequestController {
    private static final String header = "X-Sharer-User-Id";
    ItemRequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestBody ItemRequest request,
                                        @RequestHeader(header) Long userId) {
        return requestClient.createItemRequest(userId, request);
    }

    @GetMapping
    public ResponseEntity<Object> getOwnRequests(@RequestHeader(header) Long userId) {
        return requestClient.getAllByUser(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllOthersRequests(@RequestHeader(header) Long userId) {
        return requestClient.getAllFromOthers(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@PathVariable Long requestId) {
        return requestClient.getById(requestId);
    }
}