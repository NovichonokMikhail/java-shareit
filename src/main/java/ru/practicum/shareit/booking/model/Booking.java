package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.ZonedDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "bookings")
@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "booker_id")
    User booker;

    @Column(name = "start_date")
    ZonedDateTime start;

    @Column(name = "end_date")
    ZonedDateTime end;

    @Enumerated(EnumType.STRING)
    BookingStatus status = BookingStatus.WAITING;
}