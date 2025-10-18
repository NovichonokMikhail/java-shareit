package ru.practicum.server.request.service;

import ru.practicum.common.dto.request.ItemRequestDtoExtended;
import ru.practicum.common.model.ItemRequest;
import ru.practicum.common.dto.request.ItemRequestDto;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequestDto create(ItemRequest request, Long authorId);

    Collection<ItemRequestDto> getAllByUser(Long userId);

    Collection<ItemRequestDto> getAllExceptUser(Long userId);

    ItemRequestDtoExtended getById(Long requestId);
}