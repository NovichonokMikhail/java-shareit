package ru.practicum.gateway.request;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.common.dto.request.ItemRequestDto;
import ru.practicum.gateway.client.BaseClient;

@Service
public class ItemRequestClient extends BaseClient {
    public ItemRequestClient(RestTemplateBuilder builder) {
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
        return get("", userId);
    }

    public ResponseEntity<Object> getAllFromOthers(long userId) {
        return get("/all", userId);
    }

    public ResponseEntity<Object> createItemRequest(long userId, ItemRequestDto dto) {
        return post("", userId, dto);
    }
}