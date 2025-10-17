package ru.practicum.server.request.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

import static ru.practicum.server.booking.mapper.BookingMapper.getUtcNow;

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
}