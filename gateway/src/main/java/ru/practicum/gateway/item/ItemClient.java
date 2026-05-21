package ru.practicum.gateway.item;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.common.dto.item.CommentDto;
import ru.practicum.common.dto.item.ItemDto;
import ru.practicum.gateway.client.BaseClient;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    public ItemClient(RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/items"))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> getById(long itemId, long userId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> addItem(long ownerId, ItemDto itemDto) {
        return post("", ownerId, itemDto);
    }

    public ResponseEntity<Object> delete(long itemId, long ownerId) {
        return delete("/" + itemId, ownerId);
    }

    public ResponseEntity<Object> update(long itemId, long ownerId, ItemDto itemDto) {
        return patch("/" + itemId, ownerId, itemDto);
    }

    public ResponseEntity<Object> addComment(long itemId, long userId, CommentDto commentDto) {
        return post("/" + itemId + "/comment", userId, commentDto);
    }

    public ResponseEntity<Object> findItems(String text, Integer from, Integer size) {
        return get("/search?text={text}&from={from}&size={size}", null, Map.of(
                "text", text,
                "from", from,
                "size", size
        ));
    }

    public ResponseEntity<Object> findAllItemsByUser(long userId) {
        return get("", userId);
    }
}