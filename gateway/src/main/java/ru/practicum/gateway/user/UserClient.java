package ru.practicum.gateway.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.gateway.client.BaseClient;
import ru.practicum.common.dto.user.UserDto;
import ru.practicum.common.model.User;

@Service
public class UserClient extends BaseClient {
    private static final String basePath = "/users";

    @Autowired
    public UserClient(@Value("$shareit-server.url") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + basePath))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> getUserById(long id) {
        return get("/" + id);
    }

    public ResponseEntity<Object> getAllUsers() {
        return get("");
    }

    public ResponseEntity<Object> createUser(User user) {
        return post("", user);
    }

    public ResponseEntity<Object> update(long id, UserDto userDto) {
        return patch("/" + id, id, userDto);
    }

    public ResponseEntity<Object> deleteUser(long id) {
        return delete("/" + id);
    }
}