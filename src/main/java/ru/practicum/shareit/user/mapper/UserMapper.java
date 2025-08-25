package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;

public class UserMapper {
    public static UserDto userToDto(final User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User dtoToUser(final UserDto dto, final User origin) {
        return origin.toBuilder()
                .name(dto.hasName() ? dto.getName() : origin.getName())
                .email(dto.hasEmail() ? dto.getEmail() : origin.getEmail())
                .build();
    }
}