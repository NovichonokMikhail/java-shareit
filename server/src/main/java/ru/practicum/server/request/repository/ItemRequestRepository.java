package ru.practicum.server.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.model.ItemRequest;

import java.util.Collection;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    Collection<ItemRequest> findAllByAuthorId(Long authorId);

    Collection<ItemRequest> findAllByAuthorIdIsNot(Long authorId);
}