package ru.practicum.server.item.mapper;

import ru.practicum.common.dto.item.CommentDto;
import ru.practicum.server.item.model.Comment;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.user.model.User;

import java.time.Instant;
import java.util.List;

import static ru.practicum.common.util.TimeConverter.instantToLocal;

public class CommentMapper {
    public static CommentDto commentToDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getCommentText(),
                comment.getAuthor().getName(), instantToLocal(comment.getCreated()));
    }

    public static List<CommentDto> commentToDto(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::commentToDto)
                .toList();
    }

    public static Comment dtoToComment(final CommentDto dto, final User author, final Item item) {
        return new Comment(null, dto.getText(), item, author, Instant.now());
    }
}