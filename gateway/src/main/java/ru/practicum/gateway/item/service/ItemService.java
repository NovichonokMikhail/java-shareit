package ru.practicum.gateway.item.service;

import ru.practicum.gateway.item.dto.CommentDto;
import ru.practicum.gateway.item.dto.ItemDto;
import ru.practicum.gateway.item.dto.ItemDtoExtended;

import java.util.Collection;

public interface ItemService {
    ItemDto create(ItemDto item, Long ownerId);

    ItemDto remove(Long id);

    ItemDto modify(ItemDto item, long ownerId);

    Collection<ItemDto> find(String name);

    ItemDtoExtended find(Long id, long userId);

    Collection<ItemDtoExtended> findAllUserItems(Long ownerId);

    CommentDto createComment(CommentDto dto, Long itemId, Long authorId);
}