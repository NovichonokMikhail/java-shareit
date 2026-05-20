package ru.practicum.item;

import org.junit.jupiter.api.Test;
import ru.practicum.server.item.model.Comment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class CommentTest {
    Comment origin = new Comment(1L, null, null, null, null);
    Comment equal = new Comment(1L, null, null, null, null);
    Comment notEqual = new Comment(3L, null, null, null, null);

    @Test
    void checkEqual() {
        assertThat(origin, equalTo(equal));
        assertThat(origin, not(notEqual));
    }
}