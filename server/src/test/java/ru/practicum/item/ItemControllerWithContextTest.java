package ru.practicum.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.common.dto.item.CommentDto;
import ru.practicum.common.dto.item.ItemDto;
import ru.practicum.common.dto.item.ItemDtoExtended;
import ru.practicum.server.ShareItServer;
import ru.practicum.server.item.ItemController;
import ru.practicum.server.item.mapper.CommentMapper;
import ru.practicum.server.item.mapper.ItemMapper;
import ru.practicum.server.item.model.Comment;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.item.service.ItemService;
import ru.practicum.server.user.model.User;
import ru.practicum.server.user.service.UserService;
import ru.practicum.common.dto.user.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ru.practicum.common.util.Constants.headerName;

@WebMvcTest(controllers = ItemController.class)
@ContextConfiguration(classes = {ShareItServer.class})
public class ItemControllerWithContextTest {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ItemService itemService;
    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    UserDto userDto = new UserDto(1L, "test", "test@gmail.com");
    User user = new User(1L, "test", "test@gmail.com");
    Item item = new Item(1L, user, "item", null, "test item", true);
    ItemDtoExtended itemDto = ItemMapper.itemToExtendedDto(item, List.of(), null, null);

    @Test
    void createItem() throws Exception {
        when(itemService.create(any(ItemDto.class), anyLong()))
                .thenReturn(ItemMapper.itemToDto(item));

        when(userService.find(anyLong()))
                .thenReturn(userDto);

        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header(headerName, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(item.getName()), String.class))
                .andExpect(jsonPath("$.description", is(item.getDescription()), String.class))
                .andExpect(jsonPath("$.available", is(item.getAvailable()), Boolean.class));
    }

    @Test
    void shouldGetAllItemsByUser() throws Exception {
        when(itemService.findAllUserItems(anyLong()))
                .thenReturn(List.of(itemDto, itemDto, itemDto));

        mvc.perform(get("/items")
                        .header(headerName, 1L)
                        .param("from", "0")
                        .param("size", "3"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].id", is(1L), Long.class))
                .andExpect(jsonPath("$.[0].name", is(item.getName()), String.class))
                .andExpect(jsonPath("$.[0].description", is(item.getDescription()), String.class));
    }

    @Test
    void shouldGetById() throws Exception {
        when(itemService.find(anyLong(), anyLong()))
                .thenReturn(itemDto);

        mvc.perform(get("/items/1")
                        .header(headerName, 1L))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(item.getName()), String.class))
                .andExpect(jsonPath("$.description", is(item.getDescription()), String.class))
                .andExpect(jsonPath("$.available", is(item.getAvailable()), Boolean.class));
    }

    @Test
    void shouldGetItemByName() throws Exception {
        when(itemService.find(anyString()))
                .thenReturn(List.of(ItemMapper.itemToDto(item)));

        mvc.perform(get("/items/search")
                        .header(headerName, 1L)
                        .param("size", "1")
                        .param("text", "test"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name", is(item.getName()), String.class))
                .andExpect(jsonPath("$.[0].description", is(item.getDescription()), String.class))
                .andExpect(jsonPath("$.[0].available", is(item.getAvailable()), Boolean.class));
    }

    @Test
    void canCreateComment() throws Exception {
        Comment comment = new Comment(null, "comment", item, user, Instant.now());
        CommentDto dto = new CommentDto(1L, "comment", user.getName(), LocalDateTime.now());

        when(itemService.createComment(any(CommentDto.class), anyLong(), anyLong()))
                .thenReturn(dto);
        when(userService.find(anyLong()))
                .thenReturn(userDto);

        mvc.perform(post("/items/1/comment")
                    .content(mapper.writeValueAsString(CommentMapper.commentToDto(comment)))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .header(headerName, 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(dto.getText()), String.class))
                .andExpect(jsonPath("$.authorName", is(user.getName()), String.class));
    }

    @Test
    void shouldUpdateById() throws Exception {
        when(itemService.modify(any(ItemDto.class), anyLong()))
                .thenReturn(ItemMapper.itemToDto(item));

        mvc.perform(patch("/items/1")
                        .content(mapper.writeValueAsString(itemDto))
                        .header(headerName, 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name", is(item.getName()), String.class))
                .andExpect(jsonPath("$.description", is(item.getDescription()), String.class))
                .andExpect(jsonPath("$.available", is(item.getAvailable()), Boolean.class));
    }
}