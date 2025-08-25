package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.model.User;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "items")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Item {
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

    @NotBlank(message = "Invalid description")
    @Column
    String description;

    @NotNull(message = "availability may not has value of null")
    @Column(name = "is_available")
    Boolean available;
}