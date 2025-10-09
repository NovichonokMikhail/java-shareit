package ru.practicum.gateway.request.service;

import ru.practicum.gateway.request.dto.ItemRequestDtoExtended;
import ru.practicum.gateway.request.model.ItemRequest;
import ru.practicum.gateway.request.dto.ItemRequestDto;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequestDto create(ItemRequest request, Long authorId);

    Collection<ItemRequestDto> getAllByUser(Long userId);

    Collection<ItemRequestDto> getAllExceptUser(Long userId);

    ItemRequestDtoExtended getById(Long requestId);
}