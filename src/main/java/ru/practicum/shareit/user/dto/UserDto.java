package ru.practicum.shareit.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    Long id;
    String name;
    String email;

    public boolean hasName() {
        return name != null && !name.isEmpty();
    }

    public boolean hasEmail() {
        return email != null && !email.isBlank();
    }
}