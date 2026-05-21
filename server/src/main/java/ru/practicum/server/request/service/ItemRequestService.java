package ru.practicum.server.request.service;

import ru.practicum.common.dto.request.ItemRequestDto;
import ru.practicum.common.dto.request.ItemRequestDtoExtended;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequestDto create(ItemRequestDto dto, Long authorId);

    Collection<ItemRequestDto> getAllByUser(Long userId);

    Collection<ItemRequestDto> getAllExceptUser(Long userId);

    ItemRequestDtoExtended getById(Long requestId);
}