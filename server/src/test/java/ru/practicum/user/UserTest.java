package ru.practicum.user;

import org.junit.jupiter.api.Test;
import ru.practicum.server.user.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {
    final User item = new User(1L, "test", "test");
    final User equalUser = new User(1L, "test", "test");
    final User otherUser = new User(7L, "t", "t");

    @Test
    public void checkEquals() {
        assertThat(item, equalTo(equalUser));
        assertThat(item, not(otherUser));
    }
}