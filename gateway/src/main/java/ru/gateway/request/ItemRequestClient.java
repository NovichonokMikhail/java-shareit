package ru.gateway.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.gateway.client.BaseClient;
import ru.practicum.server.request.model.ItemRequest;

@Service
public class ItemRequestClient extends BaseClient {
    @Autowired
    public ItemRequestClient(@Value("$shareit-server.url") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/requests"))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> getById(long requestId) {
        return get("/" + requestId);
    }

    public ResponseEntity<Object> getAllByUser(long userId) {
        return get("/all", userId);
    }

    public ResponseEntity<Object> getAllFromOthers(long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> createItemRequest(long userId, ItemRequest request) {
        return post("", userId, request);
    }
}