package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemDtoMapper {
    public static Item dtoToItem(ItemDto dto, Item origin) {
        if (dto.hasDescription())
            origin.setDescription(dto.getDescription());
        if (dto.hasName())
            origin.setName(dto.getName());
        if (dto.hasAvailable())
            origin.setAvailable(dto.getAvailable());
        return origin;
    }

    public static ItemDto itemToDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }
}