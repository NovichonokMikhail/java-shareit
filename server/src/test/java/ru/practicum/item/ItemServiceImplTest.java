package ru.practicum.item;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.common.dto.booking.BookingStatus;
import ru.practicum.common.dto.item.CommentDto;
import ru.practicum.common.dto.item.ItemDto;
import ru.practicum.common.dto.item.ItemDtoExtended;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.exception.ValidationException;
import ru.practicum.server.booking.model.Booking;
import ru.practicum.server.booking.repository.BookingRepository;
import ru.practicum.server.item.mapper.CommentMapper;
import ru.practicum.server.item.mapper.ItemMapper;
import ru.practicum.server.item.model.Comment;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.item.repository.CommentRepository;
import ru.practicum.server.item.repository.ItemRepository;
import ru.practicum.server.item.service.ItemServiceImpl;
import ru.practicum.server.user.model.User;
import ru.practicum.server.user.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static ru.practicum.common.util.TimeConverter.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(makeFinal = true)
public class ItemServiceImplTest {
     @NonFinal
     @Mock
     ItemRepository itemRepository;
     @NonFinal
     @Mock
     UserRepository userRepository;
     @NonFinal
     @Mock
     BookingRepository bookingRepository;
     @NonFinal
     @Mock
     CommentRepository commentRepository;

     @NonFinal
     @InjectMocks
     ItemServiceImpl itemService;

     Instant now = Instant.now();
     final User user = new User(1L, null, null);
     final User otherUser = new User(2L, null, null);
     final Item item = new Item(1L, user, "test", null, "t", true);
     final Comment comment = new Comment(1L, "test", item, otherUser, now);
     final CommentDto commentDto = CommentMapper.commentToDto(comment);
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
     void canFailCreation() {
         assertThrows(NotFoundException.class, () -> itemService.create(dto, 1L));
     }

     @Test
     void canRemoveItem() {
         Mockito.when(itemRepository.findById(anyLong()))
                 .thenReturn(Optional.of(item));
         ItemDto removedItem = itemService.remove(1L);
         assertThat(removedItem, equalTo(dto));
     }

     @Test
     void canFailRemoval() {
         assertThrows(NotFoundException.class, () -> itemService.remove(1L));
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
     void canNotAllowModification() {
         Mockito.when(itemRepository.findById(anyLong()))
                 .thenReturn(Optional.of(item));
         assertThrows(NotFoundException.class, () -> itemService.modify(dto, 2L));
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

     @Test
     void ownerCantCreateComment() {
         Mockito.when(userRepository.findById(anyLong()))
                 .thenReturn(Optional.of(user));
         Mockito.when(itemRepository.findById(anyLong()))
                 .thenReturn(Optional.of(item));
         assertThrows(ValidationException.class, () -> itemService.createComment(commentDto, 1L, 1L));
     }

    @Test
    void userCantCreateCommentWithoutBooking() {
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));
        Mockito.when(bookingRepository.findAllByBookerIdAndItemIdAndStatusIs(anyLong(), anyLong(),
                        ArgumentMatchers.any(BookingStatus.class)))
                .thenReturn(List.of());
        assertThrows(ValidationException.class, () -> itemService.createComment(commentDto, 1L, 2L));
    }

    @Test
    void userCantCreateCommentBeforeEndOfBooking() {
        Instant nowMinusHour = localToInstant(instantToLocal(now).minusHours(1));
        Instant nowPlusMinute = localToInstant(instantToLocal(now).plusMinutes(1));
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));
        Mockito.when(bookingRepository.findAllByBookerIdAndItemIdAndStatusIs(anyLong(), anyLong(),
                        ArgumentMatchers.any(BookingStatus.class)))
                .thenReturn(List.of(new Booking(1L, item, otherUser, nowMinusHour, nowPlusMinute,
                        BookingStatus.APPROVED)));
        assertThrows(ValidationException.class, () -> itemService.createComment(commentDto, 1L, 2L));
    }

    @Test
    void canCreateComment() {
        Instant nowMinusHour = localToInstant(instantToLocal(now).minusHours(1));
        Instant nowMinusMinute = localToInstant(instantToLocal(now).minusMinutes(1));
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));
        Mockito.when(bookingRepository.findAllByBookerIdAndItemIdAndStatusIs(anyLong(), anyLong(),
                        ArgumentMatchers.any(BookingStatus.class)))
                .thenReturn(List.of(new Booking(1L, item, otherUser, nowMinusHour, nowMinusMinute,
                        BookingStatus.APPROVED)));
        Mockito.when(commentRepository.save(ArgumentMatchers.any(Comment.class)))
                        .thenReturn(comment);
        assertThat(itemService.createComment(commentDto, 1L, 2L), equalTo(commentDto));
    }
}