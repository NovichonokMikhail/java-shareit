package ru.practicum.gateway.user.service;

import ru.practicum.gateway.user.model.User;
import ru.practicum.gateway.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto create(User user);

    UserDto remove(Long id);

    UserDto modify(UserDto dto);

    UserDto find(Long id);

    Collection<UserDto> findAll();
}