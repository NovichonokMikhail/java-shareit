package ru.practicum.gateway.item;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.dto.item.CommentDto;
import ru.practicum.common.dto.item.ItemDto;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ItemController {
    ItemClient itemClient;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items; X-Sharer-User-Id: {}", userId);
        return itemClient.findAllItemsByUser(userId);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findById(@PathVariable final Long itemId,
                                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items/{}", itemId);
        return itemClient.getById(itemId, userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findByName(@RequestParam final String text,
                                          @RequestParam final Integer from,
                                          @RequestParam final Integer size) {
        log.info("GET /items/search");
        return itemClient.findItems(text, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createItem(@Valid @RequestBody final ItemDto dto,
                              @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("POST /items; X-Sharer-User-Id: {}", ownerId);
        return itemClient.addItem(ownerId, dto);
    }

    @PostMapping("{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createComment(@PathVariable Long itemId,
                                    @RequestBody CommentDto dto,
                                    @RequestHeader("X-Sharer-User-Id") Long authorId) {
        log.info("POST /items/{}/comment; X-Sharer-User-Id={}", itemId, authorId);
        return itemClient.addComment(itemId, authorId, dto);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> modifyItem(@PathVariable final Long itemId,
                              @RequestBody final ItemDto dto,
                              @RequestHeader("X-Sharer-User-Id") final Long ownerId) {
        log.info("PATCH /items/{}; X-Sharer-User-Id: {}", itemId, ownerId);
        dto.setId(itemId);
        return itemClient.update(itemId, ownerId, dto);
    }
}