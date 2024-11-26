package com.acmeplex.service;

import com.acmeplex.model.Showtime;
import com.acmeplex.model.Seat;
import com.acmeplex.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The SeatService class provides business logic related to seats for a particular showtime.
 * It interacts with the SeatRepository to perform CRUD operations and implement seat booking logic.
 * Author: Riley Koppang
 */
@Service
public class SeatService {

    // Injecting the SeatRepository to interact with the database
    private final SeatRepository seatRepository;

    // Constructor-based injection to initialize the SeatRepository
    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    /**
     * Retrieves a list of seats for a given showtime.
     * 
     * @param showtime The Showtime object for which the seats are to be retrieved.
     * @return A list of Seat objects associated with the provided showtime.
     */
    public List<Seat> getSeatsByShowtime(Showtime showtime) {
        // Uses the repository's findByShowtime() method to get seats associated with the provided showtime
        return seatRepository.findByShowtime(showtime);
    }

    /**
     * Retrieves a specific seat by its seat number and showtime.
     * 
     * @param seatNumber The seat number to retrieve.
     * @param showtime The Showtime object associated with the seat.
     * @return An Optional containing the Seat object if found, otherwise empty.
     */
    public Optional<Seat> getSeatByNumberAndShowtime(int seatNumber, Showtime showtime) {
        // Uses the repository's findBySeatNumberAndShowtime() method to retrieve a specific seat by number and showtime
        return seatRepository.findBySeatNumberAndShowtime(seatNumber, showtime);
    }

    /**
     * Checks whether a seat is already booked for a specific showtime.
     * 
     * @param showtimeId The ID of the showtime.
     * @param seatNumber The seat number to check.
     * @param seatRow The row of the seat to check.
     * @return true if the seat is booked, false otherwise.
     */
    public boolean isSeatBooked(int showtimeId, int seatNumber, int seatRow) {
        // Uses the repository's existsBySeatNumberAndSeatRowAndShowtime_ShowtimeId() method to check if the seat is booked
        return seatRepository.existsBySeatNumberAndSeatRowAndShowtime_ShowtimeId(seatNumber, seatRow, showtimeId);
    }

    /**
     * Books multiple seats by saving them to the database.
     * 
     * @param seats A list of Seat objects to save.
     * @return A list of saved Seat objects.
     */
    public List<Seat> saveAllSeats(List<Seat> seats) {
        // Saves the list of seats to the database using the repository's saveAll() method
        return seatRepository.saveAll(seats);
    }
}
