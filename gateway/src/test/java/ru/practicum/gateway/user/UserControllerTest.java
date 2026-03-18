package ru.practicum.gateway.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.common.dto.user.UserDto;
import ru.practicum.gateway.ShareItGateway;

@ContextConfiguration(classes = ShareItGateway.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    UserController userController;

    UserDto user = new UserDto(1L, "username", "email@ya.ru");

//    @Test
//    public void shouldFailAddUserWithoutName() throws Exception {
//        user = new UserDto(1L, null, "email@ya.ru");
//
//        mvc.perform(post("/users")
//                        .content(mapper.writeValueAsString(user))
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().is(400));
//    }
//
//    @Test
//    public void shouldFailAddUserWithWrongEmail() throws Exception {
//        user = new UserDto(1L, "username", " ");
//
//        mvc.perform(post("/users")
//                        .content(mapper.writeValueAsString(user))
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().is(400));
//    }
}