package ru.practicum.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.common.dto.item.ItemDto;
import ru.practicum.common.dto.item.ItemDtoExtended;
import ru.practicum.server.booking.repository.BookingRepository;
import ru.practicum.server.item.mapper.ItemMapper;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.item.repository.CommentRepository;
import ru.practicum.server.item.repository.ItemRepository;
import ru.practicum.server.item.service.ItemServiceImpl;
import ru.practicum.server.user.model.User;
import ru.practicum.server.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

     @Mock
     ItemRepository itemRepository;
     @Mock
     UserRepository userRepository;
     @Mock
     BookingRepository bookingRepository;
     @Mock
     CommentRepository commentRepository;

     @InjectMocks
     ItemServiceImpl itemService;

     final User user = new User(1L, null, null);
     final Item item = new Item(1L, user, "test", null, "t", true);
     final ItemDto dto = ItemMapper.itemToDto(item);
     final ItemDtoExtended extended = ItemMapper.itemToExtendedDto(item, List.of(), null, null);

     @Test
     void canCreateItem() {
         Mockito.when(userRepository.findById(anyLong()))
                 .thenReturn(Optional.of(user));
         Mockito.when(itemRepository.save(ArgumentMatchers.any(Item.class)))
                 .thenReturn(item);
         ItemDto savedItem = itemService.create(dto, 1L);
         assertThat(savedItem, equalTo(dto));
     }

     @Test
     void canRemoveItem() {
         Mockito.when(itemRepository.findById(anyLong()))
                 .thenReturn(Optional.of(item));
         ItemDto removedItem = itemService.remove(1L);
         assertThat(removedItem, equalTo(dto));
     }

     @Test
     void canModify() {
         Mockito.when(itemRepository.findById(anyLong()))
                 .thenReturn(Optional.of(item));
         Mockito.when(itemRepository.save(ArgumentMatchers.any(Item.class)))
                 .thenReturn(item);
         ItemDto savedItem = itemService.modify(dto, 1L);
         assertThat(savedItem, equalTo(dto));
     }

     @Test
     void canFindById() {
         Mockito.when(itemRepository.findById(anyLong()))
                 .thenReturn(Optional.of(item));
         ItemDtoExtended response = itemService.find(1L, 1L);
         assertThat(response, equalTo(extended));
     }

     @Test
     void canFindByText() {
         Mockito.when(itemRepository.findAllAvailableByText(anyString()))
                 .thenReturn(List.of(item));
         Collection<ItemDto> dtos = itemService.find("test");
         assertThat(dtos.size(), equalTo(1));
         assertThat(new ArrayList<>(dtos).getFirst(), equalTo(dto));
     }

     @Test
     void canFindAllUserItems() {
         Mockito.when(userRepository.findById(anyLong()))
                 .thenReturn(Optional.of(user));
         Mockito.when(itemRepository.findAllByOwnerId(anyLong()))
                 .thenReturn(List.of(item, item));
         Collection<ItemDtoExtended> dtos = itemService.findAllUserItems(1L);
         assertThat(dtos.size(), equalTo(2));
         assertThat(new ArrayList<>(dtos).getFirst(), equalTo(extended));
     }
}