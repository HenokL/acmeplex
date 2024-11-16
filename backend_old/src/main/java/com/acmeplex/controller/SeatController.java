package com.acmeplex.controller;

import com.acmeplex.model.Seat;
import com.acmeplex.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")  // Base URL for all seat-related endpoints
public class SeatController {

    private final SeatService seatService;

    // Constructor-based dependency injection for SeatService
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // Get all available seats for a specific showtime
    @GetMapping("/available/{showtimeId}")
    public ResponseEntity<List<Seat>> getAvailableSeatsByShowtime(@PathVariable Long showtimeId) {
        List<Seat> seats = seatService.getAvailableSeatsByShowtime(showtimeId);
        if (seats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No available seats found
        }
        return new ResponseEntity<>(seats, HttpStatus.OK);  // Return available seats
    }

    // Get a specific seat by seat number and showtime ID
    @GetMapping("/seat/{seatNumber}/{showtimeId}")
    public ResponseEntity<Seat> getSeatBySeatNumberAndShowtime(@PathVariable int seatNumber, @PathVariable Long showtimeId) {
        Seat seat = seatService.getSeatBySeatNumberAndShowtime(seatNumber, showtimeId);
        if (seat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Seat not found
        }
        return new ResponseEntity<>(seat, HttpStatus.OK);  // Return found seat
    }

    // Get all seats for a specific showtime (regardless of availability)
    @GetMapping("/all/{showtimeId}")
    public ResponseEntity<List<Seat>> getAllSeatsByShowtime(@PathVariable Long showtimeId) {
        List<Seat> seats = seatService.getAllSeatsByShowtime(showtimeId);
        if (seats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No seats found
        }
        return new ResponseEntity<>(seats, HttpStatus.OK);  // Return all seats
    }

    // Get all available seats in the system
    @GetMapping("/available")
    public ResponseEntity<List<Seat>> getAllAvailableSeats() {
        List<Seat> seats = seatService.getAllAvailableSeats();
        if (seats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No available seats found
        }
        return new ResponseEntity<>(seats, HttpStatus.OK);  // Return all available seats
    }

    // Get all unavailable (reserved) seats
    @GetMapping("/unavailable")
    public ResponseEntity<List<Seat>> getAllUnavailableSeats() {
        List<Seat> seats = seatService.getAllUnavailableSeats();
        if (seats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No unavailable seats found
        }
        return new ResponseEntity<>(seats, HttpStatus.OK);  // Return all unavailable seats
    }

    // Get seats by row for a specific showtime
    @GetMapping("/row/{row}/{showtimeId}")
    public ResponseEntity<List<Seat>> getSeatsByRowAndShowtime(@PathVariable int row, @PathVariable Long showtimeId) {
        List<Seat> seats = seatService.getSeatsByRowAndShowtime(row, showtimeId);
        if (seats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No seats found for this row
        }
        return new ResponseEntity<>(seats, HttpStatus.OK);  // Return seats in the specified row
    }

    // Create or update a seat
    @PostMapping
    public ResponseEntity<Seat> createOrUpdateSeat(@RequestBody Seat seat) {
        Seat savedSeat = seatService.createOrUpdateSeat(seat);
        return new ResponseEntity<>(savedSeat, HttpStatus.CREATED);  // Return created/updated seat with status 201
    }

    // Delete a seat by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return status 204 No Content (successful deletion)
    }
}
