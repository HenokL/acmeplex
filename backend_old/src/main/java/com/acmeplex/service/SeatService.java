package com.acmeplex.service;

import com.acmeplex.model.Seat;
import com.acmeplex.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    // Constructor-based dependency injection for SeatRepository
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    // Get all available seats for a specific showtime
    public List<Seat> getAvailableSeatsByShowtime(Long showtimeId) {
        return seatRepository.findByShowtimeIdAndAvailableTrue(showtimeId);
    }

    // Get a specific seat by seat number and showtime ID
    public Seat getSeatBySeatNumberAndShowtime(int seatNumber, Long showtimeId) {
        return seatRepository.findBySeatNumberAndShowtimeId(seatNumber, showtimeId);
    }

    // Get all seats for a specific showtime (regardless of availability)
    public List<Seat> getAllSeatsByShowtime(Long showtimeId) {
        return seatRepository.findByShowtimeId(showtimeId);
    }

    // Get all available seats in the system
    public List<Seat> getAllAvailableSeats() {
        return seatRepository.findByAvailableTrue();
    }

    // Get all unavailable (reserved) seats
    public List<Seat> getAllUnavailableSeats() {
        return seatRepository.findByAvailableFalse();
    }

    // Get seats by row for a specific showtime
    public List<Seat> getSeatsByRowAndShowtime(int row, Long showtimeId) {
        return seatRepository.findByRowAndShowtimeId(row, showtimeId);
    }

    // Create or update a seat
    public Seat createOrUpdateSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    // Delete a seat by its ID
    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }
}
