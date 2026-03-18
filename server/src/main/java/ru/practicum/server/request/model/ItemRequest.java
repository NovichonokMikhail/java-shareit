package ru.practicum.server.request.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    LocalDateTime created = LocalDateTime.now();
//
//    public static LocalDateTime utcToLocal(ZonedDateTime zonedTime) {
//        return zonedTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
//    }
//
//    public static ZonedDateTime localToUtc(LocalDateTime localTime) {
//        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
//    }
//
//    public static ZonedDateTime getUtcNow() {
//        return ZonedDateTime.now(ZoneOffset.UTC);
//    }
}