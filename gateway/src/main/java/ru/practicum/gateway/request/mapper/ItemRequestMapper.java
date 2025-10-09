package ru.practicum.gateway.request.mapper;

import ru.practicum.gateway.item.mapper.ItemMapper;
import ru.practicum.gateway.item.model.Item;
import ru.practicum.gateway.request.dto.ItemRequestDtoExtended;
import ru.practicum.gateway.request.model.ItemRequest;
import ru.practicum.gateway.request.dto.ItemRequestDto;

import java.util.List;

import static ru.practicum.gateway.booking.mapper.BookingMapper.utcToLocal;

public class ItemRequestMapper {
    public static ItemRequestDto requestTotoDto(ItemRequest request) {
        return new ItemRequestDto(request.getId(), request.getDescription(), utcToLocal(request.getCreated()));
    }

    public static ItemRequestDtoExtended requestToDtoExtended(ItemRequest request, List<Item> items) {
        return new ItemRequestDtoExtended(request.getId(), request.getDescription(), utcToLocal(request.getCreated()),
                items.stream().map(ItemMapper::itemToDto).toList());
    }
}