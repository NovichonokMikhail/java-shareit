package ru.practicum.server.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.common.dto.booking.BookingStatus;
import ru.practicum.server.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findAllByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findAllByBookerIdAndItemIdAndStatusIs(Long authorId, Long itemId, BookingStatus status);

    Optional<Booking> findTop1ByItemIdAndEndBeforeOrderByStartDesc(Long itemId, LocalDateTime end);

    Optional<Booking> findTop1ByItemIdAndStartAfterOrderByStartAsc(Long itemId, LocalDateTime end);
}