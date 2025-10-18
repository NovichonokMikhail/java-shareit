package ru.practicum.server.request.mapper;

import ru.practicum.server.item.mapper.ItemMapper;
import ru.practicum.common.model.Item;
import ru.practicum.common.dto.request.ItemRequestDtoExtended;
import ru.practicum.common.model.ItemRequest;
import ru.practicum.common.dto.request.ItemRequestDto;

import java.util.List;

import static ru.practicum.common.model.ItemRequest.utcToLocal;

public class ItemRequestMapper {
    public static ItemRequestDto requestTotoDto(ItemRequest request) {
        return new ItemRequestDto(request.getId(), request.getDescription(), utcToLocal(request.getCreated()));
    }

    public static ItemRequestDtoExtended requestToDtoExtended(ItemRequest request, List<Item> items) {
        return new ItemRequestDtoExtended(request.getId(), request.getDescription(), utcToLocal(request.getCreated()),
                items.stream().map(ItemMapper::itemToDto).toList());
    }
}