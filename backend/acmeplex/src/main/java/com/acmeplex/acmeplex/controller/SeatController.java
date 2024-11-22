package com.acmeplex.controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.acmeplex.model.Showtime;
import com.acmeplex.model.Seat;
import com.acmeplex.service.ShowtimeService;
import com.acmeplex.service.SeatService;
import com.acmeplex.dto.SeatResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors; 


@RestController
@RequestMapping("/api/seats")
public class SeatController {

    
    private final ShowtimeService showtimeService;
    private final SeatService seatService; 

    // Constructor-based dependency injection
    @Autowired
    public SeatController(SeatService seatService, ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
        this.seatService = seatService; 
    }

    // Get booked seats for a specific showtime
    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<List<SeatResponseDTO>> getSeatsByShowtime(@PathVariable int showtimeId) {

        // Get specified showtime
        Optional<Showtime> optionalShowtime = showtimeService.getShowtimeById(showtimeId);
        if (optionalShowtime.isPresent()) {
            // Showtime exists - get the booked seats
            List<Seat> seats = seatService.getSeatsByShowtime(optionalShowtime.get());

            // Convert Seat entities to SeatResponseDTO
            List<SeatResponseDTO> seatResponseList = seats.stream()
                    .map(seat -> new SeatResponseDTO(seat.getSeatNumber(), seat.getSeatRow()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(seatResponseList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /*
    
    // Book a seat for a showtime@PostMapping("/book")
    @PostMapping("/book")
    public ResponseEntity<String> bookSeats(@RequestBody Map<String, Object> request) {
        // Extract the showtimeId from the request
        int showtimeId = (int) request.get("showtimeId");

        // Fetch the showtime by ID
        Optional<Showtime> optionalShowtime = showtimeService.getShowtimeById(showtimeId);
        if (!optionalShowtime.isPresent()) {
            return new ResponseEntity<>("Showtime not found", HttpStatus.NOT_FOUND);
        }

        Showtime showtime = optionalShowtime.get();

        // Extract the seats array from the request
        List<Map<String, Object>> seatsRequest = (List<Map<String, Object>>) request.get("seats");

        // List to store booked seats
        List<Seat> bookedSeats = new ArrayList<>();

        // Check if any of the seats are already booked
        for (Map<String, Object> seatData : seatsRequest) {
            int seatNumber = (int) seatData.get("seatNumber");
            int seatRow = (int) seatData.get("seatRow");

            // Check if the seat is already booked for the given showtime
            Optional<Seat> existingSeat = seatService.getSeatByNumberAndShowtime(seatNumber, showtime);
            if (existingSeat.isPresent()) {
                // If any seat is already booked, return a bad request response without saving any seat
                return new ResponseEntity<>("Seat " + seatNumber + " in row " + seatRow + " is already booked", HttpStatus.BAD_REQUEST);
            }

            // Otherwise, create a new seat object to book
            Seat bookedSeat = new Seat(seatNumber, seatRow, showtime);
            bookedSeats.add(bookedSeat);
        }

        // If no seats were found to be booked, proceed with saving all seats
        for (Seat bookedSeat : bookedSeats) {
            seatService.saveSeat(bookedSeat);
        }

        // Return success message with status CREATED
        return new ResponseEntity<>("Seats booked successfully", HttpStatus.CREATED);
    }

    */
}
