package ru.practicum.booking;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.common.util.Constants.headerName;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.common.dto.booking.*;
import ru.practicum.common.dto.user.UserDto;
import ru.practicum.server.ShareItServer;
import ru.practicum.server.booking.BookingController;
import ru.practicum.server.booking.service.BookingService;
import ru.practicum.server.user.mapper.UserMapper;
import ru.practicum.server.user.model.User;

@WebMvcTest(controllers = BookingController.class)
@ContextConfiguration(classes = { ShareItServer.class })
public class BookingControllerWithContextTest {

    @MockBean
    BookingService bookingService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    User booker = new User(2L, "booker", "booker@example.com");
    UserDto bookerDto = UserMapper.userToDto(booker);
    LocalDateTime nowPlus10s = LocalDateTime.now()
        .truncatedTo(ChronoUnit.SECONDS)
        .plusSeconds(10);
    LocalDateTime nowPlusMin = LocalDateTime.now()
        .plusMinutes(1)
        .truncatedTo(ChronoUnit.SECONDS);
    BookingDtoCreation inDto = new BookingDtoCreation(
        nowPlusMin,
        nowPlus10s,
        BookingStatus.WAITING,
        2L,
        1L
    );
    BookingDtoResponse outDto = new BookingDtoResponse(
        1L,
        nowPlusMin,
        nowPlus10s,
        BookingStatus.APPROVED,
        bookerDto,
        null
    );

    @Test
    public void shouldMakeBooking() throws Exception {
        when(bookingService.create(any(BookingDtoCreation.class), anyLong()))
                .thenReturn(outDto);

        mvc.perform(post("/bookings")
                        .header(headerName, 1L)
                        .content(mapper.writeValueAsString(inDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(outDto.getId()), Long.class))
            .andExpect(jsonPath("$.start", is(outDto.getStart().toString()), String.class))
            .andExpect(jsonPath("$.end", is(outDto.getEnd().toString()), String.class));
    }

    @Test
    public void shouldUpdateStatus() throws Exception {
        when(
            bookingService.judge(anyLong(), anyLong(), any(BookingStatus.class))
        ).thenReturn(outDto);

        mvc
            .perform(
                patch("/bookings/7")
                    .header("X-Sharer-User-Id", 1L)
                    .param("approved", "true")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(outDto.getId()), Long.class))
            .andExpect(
                jsonPath(
                    "$.start",
                    is(inDto.getStart().toString()),
                    String.class
                )
            )
            .andExpect(
                jsonPath("$.end", is(inDto.getEnd().toString()), String.class)
            )
            .andExpect(
                jsonPath(
                    "$.status",
                    is(outDto.getStatus().toString()),
                    String.class
                )
            );
    }

    @Test
    public void shouldGetById() throws Exception {
        when(bookingService.get(anyLong(), anyLong()))
        .thenReturn(outDto);

        mvc
            .perform(get("/bookings/1").header("X-Sharer-User-Id", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(outDto.getId()), Long.class))
            .andExpect(
                jsonPath(
                    "$.start",
                    is(inDto.getStart().toString()),
                    String.class
                )
            )
            .andExpect(
                jsonPath("$.end", is(inDto.getEnd().toString()), String.class)
            )
            .andExpect(
                jsonPath(
                    "$.status",
                    is(outDto.getStatus().toString()),
                    String.class
                )
            );
    }

    @Test
    public void shouldGetAllByBookerId() throws Exception {
        when(
            bookingService.getAllByBookerAndState(
                anyLong(),
                any(BookingState.class)
            )
        ).thenReturn(List.of(outDto));

        mvc
            .perform(get("/bookings").header("X-Sharer-User-Id", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[0].id", is(outDto.getId()), Long.class))
            .andExpect(
                jsonPath(
                    "$.[0].start",
                    is(inDto.getStart().toString()),
                    String.class
                )
            )
            .andExpect(
                jsonPath(
                    "$.[0].end",
                    is(inDto.getEnd().toString()),
                    String.class
                )
            )
            .andExpect(
                jsonPath(
                    "$.[0].status",
                    is(outDto.getStatus().toString()),
                    String.class
                )
            );
    }

    @Test
    public void shouldGetAllByOwnerId() throws Exception {
        when(
            bookingService.getAllByOwnerAndState(anyLong(), any(BookingState.class))
        ).thenReturn(List.of(outDto));

        mvc
            .perform(get("/bookings/owner").header("X-Sharer-User-Id", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[0].id", is(outDto.getId()), Long.class))
            .andExpect(
                jsonPath(
                    "$.[0].start",
                    is(inDto.getStart().toString()),
                    String.class
                )
            )
            .andExpect(
                jsonPath(
                    "$.[0].end",
                    is(inDto.getEnd().toString()),
                    String.class
                )
            )
            .andExpect(
                jsonPath(
                    "$.[0].status",
                    is(outDto.getStatus().toString()),
                    String.class
                )
            );
    }
}