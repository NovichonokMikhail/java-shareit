package ru.practicum.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.practicum.common.dto.request.ItemRequestDto;
import ru.practicum.common.dto.request.ItemRequestDtoExtended;
import ru.practicum.server.booking.repository.BookingRepository;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.item.repository.ItemRepository;
import ru.practicum.server.request.mapper.ItemRequestMapper;
import ru.practicum.server.request.model.ItemRequest;
import ru.practicum.server.request.repository.ItemRequestRepository;
import ru.practicum.server.request.service.ItemRequestServiceImpl;
import ru.practicum.server.user.model.User;
import ru.practicum.server.user.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceTest {
    @Mock
    ItemRequestRepository requestRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    BookingRepository bookingRepository;

    @InjectMocks
    ItemRequestServiceImpl itemRequestService;

    final ItemRequest request = new ItemRequest(1L, 1L, "description", Instant.now());
    final User user = new User();
    final Item item = new Item();
    final ItemRequestDto dto = ItemRequestMapper.requestTotoDto(request);
    final ItemRequestDtoExtended extendedDto = ItemRequestMapper.requestToDtoExtended(request, List.of(item));

    @Test
    void requestCanBeCreated() {
        Mockito.when(requestRepository.save(ArgumentMatchers.any(ItemRequest.class)))
            .thenReturn(request);
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(user));

        ItemRequestDto savedItem = itemRequestService.create(dto, 1L);
        assertThat(dto, equalTo(savedItem));
    }

    @Test
    void canGetAllByUser() {
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(user));
        Mockito.when(requestRepository.findAllByAuthorId(anyLong()))
                .thenReturn(List.of(request));
        Collection<ItemRequestDto> saved = itemRequestService.getAllByUser(3L);
        assertThat(saved.size(), equalTo(1));
        assertThat(new ArrayList<>(saved).getFirst(), equalTo(dto));
    }

    @Test
    void canGetAllExceptUser() {
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(user));
        Mockito.when(requestRepository.findAllByAuthorIdIsNot(anyLong()))
                .thenReturn(List.of(request));
        Collection<ItemRequestDto> saved = itemRequestService.getAllExceptUser(3L);
        assertThat(saved.size(), equalTo(1));
        assertThat(new ArrayList<>(saved).getFirst(), equalTo(dto));
    }

    @Test
    void requestCanBeFoundById() {
        Mockito.when(requestRepository.findById(anyLong()))
                .thenReturn(Optional.of(request));
        Mockito.when(itemRepository.findAllByRequestId(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(item));

        ItemRequestDtoExtended request = itemRequestService.getById(3L);
        assertThat(request, equalTo(extendedDto));
    }
}