package ru.practicum.gateway.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.gateway.booking.model.Booking;
import ru.practicum.gateway.booking.model.BookingStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findAllByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findAllByBookerIdAndItemIdAndStatusIs(Long authorId, Long itemId, BookingStatus status);

    Optional<Booking> findTop1ByItemIdAndEndBeforeOrderByStartDesc(Long itemId, ZonedDateTime end);

    Optional<Booking> findTop1ByItemIdAndStartAfterOrderByStartAsc(Long itemId, ZonedDateTime end);
}