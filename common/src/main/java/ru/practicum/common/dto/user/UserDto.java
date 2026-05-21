package ru.practicum.common.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.common.util.OnCreate;
import ru.practicum.common.util.OnUpdate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto {
    @EqualsAndHashCode.Include
    Long id;
    @NotBlank(message = "Name must not be blank", groups = OnCreate.class)
    String name;
    @Email(message = "Email should be valid", groups = {OnCreate.class, OnUpdate.class})
    @NotBlank(message = "Email must not be blank", groups = OnCreate.class)
    String email;

    public boolean hasName() {
        return name != null && !name.isEmpty();
    }

    public boolean hasEmail() {
        return email != null && !email.isBlank();
    }
}