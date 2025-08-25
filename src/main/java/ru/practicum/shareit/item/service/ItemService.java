package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoExtended;

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