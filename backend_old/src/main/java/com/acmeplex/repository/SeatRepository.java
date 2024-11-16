package com.acmeplex.repository;

import com.acmeplex.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Find all seats available for a specific showtime
    List<Seat> findByShowtimeIdAndAvailableTrue(Long showtimeId);

    // Find a seat by its seat number for a specific showtime
    Seat findBySeatNumberAndShowtimeId(int seatNumber, Long showtimeId);

    // Find all seats for a specific showtime
    List<Seat> findByShowtimeId(Long showtimeId);

    // Find all available seats
    List<Seat> findByAvailableTrue();

    // Find all unavailable (reserved) seats
    List<Seat> findByAvailableFalse();

    // Find seats by row for a specific showtime
    List<Seat> findByRowAndShowtimeId(int row, Long showtimeId);
}
