package ru.practicum.server.util;

import java.util.Collection;
import java.util.Optional;

public interface BaseStorage<T> {
    Optional<T> get(Long id);

    T create(T obj);

    T update(T obj);

    T delete(Long id);

    Collection<T> findAll();
}