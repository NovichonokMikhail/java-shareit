package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;

public class UserDtoMapper {
    public static UserDto userToDto(final User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User dtoToUser(final UserDto dto, final User origin) {
        if (dto.hasEmail())
            origin.setEmail(dto.getEmail());
        if (dto.hasName())
            origin.setName(dto.getName());
        return origin;
    }
}