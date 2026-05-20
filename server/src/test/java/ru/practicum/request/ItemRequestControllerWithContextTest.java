package ru.practicum.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.common.dto.request.ItemRequestDto;
import ru.practicum.common.dto.request.ItemRequestDtoExtended;
import ru.practicum.common.util.TimeConverter;
import ru.practicum.server.ShareItServer;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.request.ItemRequestController;
import ru.practicum.server.request.mapper.ItemRequestMapper;
import ru.practicum.server.request.model.ItemRequest;
import ru.practicum.server.request.service.ItemRequestService;
import ru.practicum.server.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
@ContextConfiguration(classes = {ShareItServer.class})
public class ItemRequestControllerWithContextTest {

    @MockBean
    ItemRequestService itemRequestService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    final Instant now = Instant.now();
    final LocalDateTime localNow = TimeConverter.instantToLocal(now);
    ItemRequest itemRequest = new ItemRequest(1L, 1L, "dd", now);
    ItemRequestDto inputDto = new ItemRequestDto(1L, "dd", now);
    ItemRequestDto outDto = ItemRequestMapper.requestTotoDto(itemRequest);

    Item item = new Item(7L, new User(), "name", 1L, "dd", true);

    ItemRequestDtoExtended extendedDto = ItemRequestMapper.requestToDtoExtended(itemRequest, List.of(item));

    @Test
    public void shouldAddItemRequest() throws Exception {
        when(itemRequestService.create(any(ItemRequestDto.class), anyLong()))
                .thenReturn(outDto);

        mvc.perform(post("/requests")
                        .content(mapper.writeValueAsString(inputDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id", is(itemRequest.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(itemRequest.getDescription()), String.class))
                .andExpect(jsonPath("$.created", is(now.toString()), String.class));
    }

    @Test
    public void shouldGetById() throws Exception {
        when(itemRequestService.getById(anyLong()))
                .thenReturn(extendedDto);

        mvc.perform(get("/requests/1")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(extendedDto.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(itemRequest.getDescription()), String.class))
                .andExpect(jsonPath("$.created", is(localNow.toString()), String.class))
                .andExpect(jsonPath("$.items", hasSize(1)));
    }

    @Test
    public void shouldGetAllByUserId() throws Exception {
        when(itemRequestService.getAllByUser(anyLong()))
                .thenReturn(List.of(outDto));

        mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is(outDto.getId()), Long.class))
                .andExpect(jsonPath("$.[0].description", is(itemRequest.getDescription()), String.class))
                .andExpect(jsonPath("$.[0].created", is(now.toString()), String.class));
//                .andExpect(jsonPath("$.[0].items", hasSize(1)))
//                .andExpect(jsonPath("$.[0].items[0]", is(item), Item.class));
    }

    @Test
    public void shouldGetAllOfOthersInPages() throws Exception {
        when(itemRequestService.getAllExceptUser(anyLong()))
                .thenReturn(List.of(outDto));

        mvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 10L)
                        .param("from", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is(itemRequest.getId()), Long.class))
                .andExpect(jsonPath("$.[0].description", is(itemRequest.getDescription()), String.class))
                .andExpect(jsonPath("$.[0].created", is(now.toString()), String.class));
    }
}