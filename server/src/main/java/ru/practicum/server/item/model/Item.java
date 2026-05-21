package ru.practicum.server.item.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.server.user.model.User;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "items")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Item {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    User owner;

    @NotBlank(message = "Invalid name")
    @Column
    String name;

    @Column(name = "request_id")
    Long requestId;

    @NotBlank(message = "Invalid description")
    @Column
    String description;

    @NotNull(message = "availability may not has value of null")
    @Column(name = "is_available")
    Boolean available;
}