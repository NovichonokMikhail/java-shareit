package ru.practicum.server.request.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.common.dto.request.ItemRequestDto;
import ru.practicum.common.dto.request.ItemRequestDtoExtended;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.item.repository.ItemRepository;
import ru.practicum.server.request.mapper.ItemRequestMapper;
import ru.practicum.server.request.model.ItemRequest;
import ru.practicum.server.request.repository.ItemRequestRepository;
import ru.practicum.server.user.repository.UserRepository;

import java.util.Collection;
import java.util.List;

import static ru.practicum.server.user.service.UserServiceImpl.USER_NOT_FOUND;

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
    public ItemRequestDto create(ItemRequestDto dto, Long authorId) {
        // Validation that author exists
        userRepository.findById(authorId).orElseThrow(() -> USER_NOT_FOUND);
        final ItemRequest request = ItemRequestMapper.dtoToRequest(dto, authorId);
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