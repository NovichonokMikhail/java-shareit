package ru.practicum.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "requests")
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter
    @JoinColumn(name = "author_id")
    Long authorId;

    @NotBlank(message = "Description cannot be empty")
    @Column
    String description;

    @Builder.Default
    @Column
    ZonedDateTime created = getUtcNow();

    public static LocalDateTime utcToLocal(ZonedDateTime zonedTime) {
        return zonedTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static ZonedDateTime localToUtc(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
    }

    public static ZonedDateTime getUtcNow() {
        return localToUtc(LocalDateTime.now());
    }
}