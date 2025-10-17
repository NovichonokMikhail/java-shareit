package ru.practicum.server.item.service;

import ru.practicum.common.dto.item.CommentDto;
import ru.practicum.common.dto.item.ItemDto;
import ru.practicum.common.dto.item.ItemDtoExtended;

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