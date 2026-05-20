package ru.practicum.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class ItemRequestDto {
    Long id;
    @NotBlank(message = "description cannot be blank")
    String description;
    Instant created;
}