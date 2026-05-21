package ru.practicum.item;

import org.junit.jupiter.api.Test;
import ru.practicum.common.dto.item.ItemDto;
import ru.practicum.server.item.mapper.ItemMapper;
import ru.practicum.server.item.model.Item;
import ru.practicum.server.user.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ItemMapperTest {
    @Test
    void dtoIsNotFull() {
        ItemDto dto = new ItemDto(1L, null, null, null, 1L);
        Item origin = new Item(1L, new User(), "none", 1L, "", true);
        assertThat(ItemMapper.dtoToItem(dto, origin), equalTo(origin));
    }
}