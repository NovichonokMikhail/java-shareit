package ru.practicum.gateway.request.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.gateway.exception.NotFoundException;
import ru.practicum.gateway.item.model.Item;
import ru.practicum.gateway.item.repository.ItemRepository;
import ru.practicum.gateway.request.dto.ItemRequestDtoExtended;
import ru.practicum.gateway.request.model.ItemRequest;
import ru.practicum.gateway.request.dto.ItemRequestDto;
import ru.practicum.gateway.request.mapper.ItemRequestMapper;
import ru.practicum.gateway.request.repository.ItemRequestRepository;
import ru.practicum.gateway.user.repository.UserRepository;

import java.util.Collection;
import java.util.List;

import static ru.practicum.gateway.user.service.UserServiceImpl.USER_NOT_FOUND;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    // Exceptions
    public static final NotFoundException REQUEST_NOT_FOUND = new NotFoundException("Item request does not exist");
    // Storages
    ItemRequestRepository requestRepository;
    ItemRepository itemRepository;
    UserRepository userRepository;

    @Override
    public ItemRequestDto create(ItemRequest request, Long authorId) {
        userRepository.findById(authorId).orElseThrow(() -> USER_NOT_FOUND);
        request.setAuthorId(authorId);
        return ItemRequestMapper.requestTotoDto(requestRepository.save(request));
    }

    @Override
    public Collection<ItemRequestDto> getAllByUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> USER_NOT_FOUND);
        return requestRepository.findAllByAuthorId(userId).stream()
                .map(ItemRequestMapper::requestTotoDto)
                .toList();
    }

    @Override
    public Collection<ItemRequestDto> getAllExceptUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> USER_NOT_FOUND);
        return requestRepository.findAllByAuthorIdIsNot(userId).stream()
                .map(ItemRequestMapper::requestTotoDto)
                .toList();
    }

    @Override
    public ItemRequestDtoExtended getById(Long requestId) {
        ItemRequest request = requestRepository.findById(requestId).orElseThrow(() -> REQUEST_NOT_FOUND);
        List<Item> items = itemRepository.findAllByRequestId(requestId);
        return ItemRequestMapper.requestToDtoExtended(request, items);
    }
}