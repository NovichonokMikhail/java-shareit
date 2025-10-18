package ru.practicum.server.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByItemId(Long itemId);
}