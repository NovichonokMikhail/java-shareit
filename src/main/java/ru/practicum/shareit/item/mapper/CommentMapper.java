package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.ZonedDateTime;
import java.util.List;

import static ru.practicum.shareit.booking.mapper.BookingMapper.GMT;

public class CommentMapper {
    public static CommentDto commentToDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getCommentText(),
                comment.getAuthor().getName(), BookingMapper.gmtToLocal(comment.getCreated()));
    }

    public static List<CommentDto> commentToDto(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::commentToDto)
                .toList();
    }

    public static Comment dtoToComment(final CommentDto dto, final User author, final Item item) {
        return new Comment(null, dto.getText(), item, author, ZonedDateTime.now(GMT));
    }
}