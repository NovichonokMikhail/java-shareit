package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class User {
    Long id;
    @NotBlank
    String name;
    @NotBlank
    @Email
    String email;
    Set<Long> items = new HashSet<>();
    List<Long> requests = new ArrayList<>();
}