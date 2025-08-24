package ru.practicum.shareit.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.DataConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

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
    UserRepository userStorage;

    @Override
    public UserDto create(final User user) {
        if (userStorage.findByEmail(user.getEmail()).isPresent())
            throw EMAIL_IS_TAKEN;
        final User createdUser = userStorage.save(user);
        return UserMapper.userToDto(createdUser);
    }

    @Override
    public UserDto remove(Long id) {
        final User user = userStorage.findById(id).orElseThrow(() -> USER_NOT_FOUND);
        userStorage.deleteById(id);
        return UserMapper.userToDto(user);
    }

    @Override
    public UserDto modify(UserDto dto) {
        final User origin = userStorage.findById(dto.getId()).orElseThrow(() -> USER_NOT_FOUND);
        // Check if new email is present and not taken
        if (dto.hasEmail() && userStorage.findByEmail(dto.getEmail()).isPresent())
            throw EMAIL_IS_TAKEN;
        // Update user
        User updatedUser = userStorage.save(UserMapper.dtoToUser(dto, origin));
        return UserMapper.userToDto(updatedUser);
    }

    @Override
    public UserDto find(Long id) {
        final User user = userStorage.findById(id).orElseThrow(() -> USER_NOT_FOUND);
        return UserMapper.userToDto(user);
    }

    @Override
    public Collection<UserDto> findAll() {
        return userStorage.findAll()
                .stream()
                .map(UserMapper::userToDto)
                .toList();
    }
}