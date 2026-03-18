package ru.practicum.server.user.service;

import ru.practicum.common.dto.user.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto create(UserDto dto);

    UserDto remove(Long id);

    UserDto modify(UserDto dto);

    UserDto find(Long id);

    Collection<UserDto> findAll();
}