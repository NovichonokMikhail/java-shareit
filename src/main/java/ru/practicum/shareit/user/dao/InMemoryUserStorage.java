package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.BaseStorage;

import java.util.*;

@Component
public class InMemoryUserStorage implements BaseStorage<User> {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User create(User obj) {
        final long id = generateId();
        obj.setId(id);
        users.put(id, obj);
        return users.get(id);
    }

    @Override
    public User update(User obj) {
        final long id = obj.getId();
        users.put(id, obj);
        return users.get(id);
    }

    @Override
    public User delete(Long id) {
        return users.remove(id);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    private long generateId() {
        long maxId = users.keySet().stream()
                .reduce(Long::max)
                .orElse(0L);
        return ++maxId;
    }
}