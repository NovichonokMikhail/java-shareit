package ru.practicum.shareit.item.dao;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.util.BaseStorage;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class InMemoryItemStorage implements BaseStorage<Item> {
    Map<Long, Item> items = new HashMap<>();

    @Override
    public Optional<Item> get(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public Item create(Item obj) {
        obj.setId(generateId());
        items.put(obj.getId(), obj);
        return items.get(obj.getId());
    }

    @Override
    public Item update(Item obj) {
        items.put(obj.getId(), obj);
        return items.get(obj.getId());
    }

    @Override
    public Item delete(Long id) {
        return items.remove(id);
    }

    @Override
    public Collection<Item> findAll() {
        return items.values();
    }

    private long generateId() {
        long maxId = items.keySet()
                .stream()
                .reduce(Long::max)
                .orElse(0L);
        return ++maxId;
    }
}