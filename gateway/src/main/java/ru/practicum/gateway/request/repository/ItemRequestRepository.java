package ru.practicum.gateway.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.gateway.request.model.ItemRequest;

import java.util.Collection;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    Collection<ItemRequest> findAllByAuthorId(Long authorId);

    Collection<ItemRequest> findAllByAuthorIdIsNot(Long authorId);
}