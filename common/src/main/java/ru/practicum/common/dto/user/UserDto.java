package ru.practicum.common.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import ru.practicum.common.util.OnCreate;
import ru.practicum.common.util.OnUpdate;

import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDto {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserDto user = (UserDto) o;
        return id != null && Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }
}