package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> findAllUsers() {
        log.info("GET /users");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable final Long id) {
        log.info("GET users/{}", id);
        return userService.find(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody User user) {
        log.info("POST /users");
        return userService.create(user);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable final Long id, @Valid @RequestBody final UserDto dto) {
        log.info("PATCH users/{}", id);
        dto.setId(id);
        return userService.modify(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto deleteUser(@PathVariable final Long id) {
        log.info("DELETE users/{}", id);
        return userService.remove(id);
    }
}