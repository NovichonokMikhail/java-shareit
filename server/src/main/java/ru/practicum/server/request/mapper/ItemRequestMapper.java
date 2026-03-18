package ru.practicum.server.request.mapper;

import ru.practicum.common.dto.request.ItemRequestDto;
import ru.practicum.common.dto.request.ItemRequestDtoExtended;
import ru.practicum.server.item.mapper.ItemMapper;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.request.model.ItemRequest;

import java.util.List;

//import static ru.practicum.server.request.model.ItemRequest.utcToLocal;

public class ItemRequestMapper {
    public static ItemRequest dtoToRequest(ItemRequestDto dto, Long authorId) {
        return new ItemRequest(null, authorId, dto.getDescription(), dto.getCreated());
    }

    public static ItemRequestDto requestTotoDto(ItemRequest request) {
        return new ItemRequestDto(request.getId(), request.getDescription(), request.getCreated());
    }

    public static ItemRequestDtoExtended requestToDtoExtended(ItemRequest request, List<Item> items) {
        return new ItemRequestDtoExtended(request.getId(), request.getDescription(), request.getCreated(),
                items.stream().map(ItemMapper::itemToDto).toList());
    }
}