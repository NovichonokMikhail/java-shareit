package ru.practicum.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.common.dto.user.UserDto;
import ru.practicum.server.user.mapper.UserMapper;
import ru.practicum.server.user.model.User;
import ru.practicum.server.user.repository.UserRepository;
import ru.practicum.server.user.service.UserServiceImpl;
import ru.practicum.common.exception.NotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void shouldFailUpdateOnWrongUserId() {
        Assertions.assertThrows(NotFoundException.class,
                () -> userService.modify(new UserDto(anyLong(), null, "test@example.com")));
    }

    @Test
    void userCanBeModified() throws NotFoundException {
        final long id = 3L;
        final User user = new User(id, "old", "old@example.com");
        final UserDto originDto = UserMapper.userToDto(user);
        final UserDto updatesOfUser = new UserDto(id, null, "new@example.com");

        Mockito.when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        UserDto savedUser = userService.create(originDto);
        UserDto updatedUser = userService.modify(updatesOfUser);

        assertThat(savedUser, equalTo(UserMapper.userToDto(user)));
        assertThat(updatedUser, hasProperty("email", equalTo(updatedUser.getEmail())));
    }

    @Test
    void userCanBeDeleted() {
        final User user = new User(3L, "test", "test@a.com");

        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        UserDto savedUser = userService.create(UserMapper.userToDto(user));
        UserDto removedUser = userService.remove(3L);

        assertThat(savedUser, equalTo(removedUser));
    }

    @Test
    void userCanBeFoundById() {
        User user = new User(3L, "test", "test@a.com");

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        UserDto foundUser = userService.find(3L);
        assertThat(foundUser, equalTo(UserMapper.userToDto(user)));
    }

    @Test
    void allUsersCanBeFound() {
        User user = new User(3L, "test", "test@a.com");

        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(user, user, user));
        Collection<UserDto> foundUsers = userService.findAll();
        assertThat(foundUsers.size(), equalTo(3));
    }
}