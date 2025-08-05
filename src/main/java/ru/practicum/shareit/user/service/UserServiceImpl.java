package ru.practicum.shareit.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.DataConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserDtoMapper;
import ru.practicum.shareit.util.BaseStorage;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    // Commonly Used Errors
    public static final NotFoundException USER_NOT_FOUND = new NotFoundException("User with this id does not exist");
    public static final DataConflictException EMAIL_IS_TAKEN =
            new DataConflictException("User with this email already exists");
    // Used Storages
    BaseStorage<User> userStorage;

    @Override
    public UserDto create(final User user) {
        if (emailIsTaken(user.getEmail()))
            throw EMAIL_IS_TAKEN;
        final User createdUser = userStorage.create(user);
        return UserDtoMapper.userToDto(createdUser);
    }

    @Override
    public UserDto remove(Long id) {
        userStorage.get(id).orElseThrow(() -> USER_NOT_FOUND);
        User user = userStorage.delete(id);
        return UserDtoMapper.userToDto(user);
    }

    @Override
    public UserDto modify(UserDto dto) {
        final User origin = userStorage.get(dto.getId()).orElseThrow(() -> USER_NOT_FOUND);
        if (dto.hasEmail() && emailIsTaken(dto.getEmail()))
            throw EMAIL_IS_TAKEN;
        User updatedUser = userStorage.update(UserDtoMapper.dtoToUser(dto, origin));
        return UserDtoMapper.userToDto(updatedUser);
    }

    @Override
    public UserDto find(Long id) {
        final User user = userStorage.get(id).orElseThrow(() -> USER_NOT_FOUND);
        return UserDtoMapper.userToDto(user);
    }

    @Override
    public Collection<UserDto> findAll() {
        return userStorage.findAll()
                .stream()
                .map(UserDtoMapper::userToDto)
                .toList();
    }

    private boolean emailIsTaken(final String email) {
        return userStorage.findAll()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}