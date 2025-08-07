package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto create(User user);

    UserDto remove(Long id);

    UserDto modify(UserDto dto);

    UserDto find(Long id);

    Collection<UserDto> findAll();
}