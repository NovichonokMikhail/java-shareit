package ru.practicum.item;

import org.junit.jupiter.api.Test;
import ru.practicum.server.item.model.Item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class ItemTest {
    final Item item = new Item(1L, null, null,
            null, null, null);
    final Item equalItem = new Item(1L, null, null,
            null, null, null);
    final Item notEqualItem = new Item(3L, null, null,
            null, null, null);

    @Test
    void checkEqual() {
        assertThat(item, equalTo(equalItem));
        assertThat(item, not(notEqualItem));
    }
}