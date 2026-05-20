package ru.practicum.gateway.request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.common.dto.request.ItemRequestDto;
import static ru.practicum.common.util.Constants.headerName;

@RestController
@RequestMapping(path = "/requests")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemRequestController {
    ItemRequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestBody ItemRequestDto dto,
                                        @RequestHeader(headerName) Long userId) {
        return requestClient.createItemRequest(userId, dto);
    }

    @GetMapping
    public ResponseEntity<Object> getOwnRequests(@RequestHeader(headerName) Long userId) {
        return requestClient.getAllByUser(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllOthersRequests(@RequestHeader(headerName) Long userId) {
        return requestClient.getAllFromOthers(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@PathVariable Long requestId) {
        return requestClient.getById(requestId);
    }
}