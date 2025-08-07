package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ItemController {
    ItemService itemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items; X-Sharer-User-Id: {}", userId);
        return itemService.findAllUserItems(userId);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto findById(@PathVariable final Long itemId) {
        log.info("GET /items/{}", itemId);
        return itemService.find(itemId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemDto> findByName(@RequestParam final String text) {
        log.info("GET /items/search");
        return itemService.find(text);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@Valid @RequestBody final Item item, @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("POST /items");
        return itemService.create(item, ownerId);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto modifyItem(@PathVariable final Long itemId,
                              @RequestBody final ItemDto dto,
                              @RequestHeader("X-Sharer-User-Id") final Long ownerId) {
        log.info("PATCH /items/{}; X-Sharer-User-Id: {}", itemId, ownerId);
        dto.setId(itemId);
        return itemService.modify(dto, ownerId);
    }
}