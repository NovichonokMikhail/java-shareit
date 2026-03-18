package ru.practicum.server.request;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.dto.request.ItemRequestDto;
import ru.practicum.common.dto.request.ItemRequestDtoExtended;
import ru.practicum.server.request.service.ItemRequestService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/requests")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class ItemRequestController {
    private static final String header = "X-Sharer-User-Id";
    ItemRequestService requestService;


    @PostMapping
    public ItemRequestDto createRequest(@Valid @RequestBody ItemRequestDto dto,
                                        @RequestHeader(header) Long userId) {
//        if (dto.getCreated() == null) {
//            dto.setCreated(LocalDateTime.now());
//        }
        return requestService.create(dto, userId);
    }

    @GetMapping
    public Collection<ItemRequestDto> getOwnRequests(@RequestHeader(header) Long userId) {
        return requestService.getAllByUser(userId);
    }

    @GetMapping("/all")
    public Collection<ItemRequestDto> getAllOthersRequests(@RequestHeader(header) Long userId) {
        return requestService.getAllExceptUser(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDtoExtended getRequest(@PathVariable Long requestId) {
        return requestService.getById(requestId);
    }
}