package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    ItemDto create(Item item, Long ownerId);

    ItemDto remove(Long id);

    ItemDto modify(ItemDto item, long ownerId);

    Collection<ItemDto> find(String name);

    ItemDto find(Long id);

    Collection<ItemDto> findAllUserItems(Long ownerId);
}