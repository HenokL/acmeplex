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

/**
 * SeatController handles HTTP requests related to seat bookings for a specific showtime.
 * It includes functionality to retrieve all booked seats for a given showtime.
 * Author: Riley Koppang
 */
@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final ShowtimeService showtimeService;
    private final SeatService seatService;

    /**
     * Constructor-based dependency injection for SeatService and ShowtimeService.
     * 
     * @param seatService The service handling seat-related operations.
     * @param showtimeService The service handling showtime-related operations.
     */
    @Autowired
    public SeatController(SeatService seatService, ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
        this.seatService = seatService;
    }

    /**
     * Retrieves all booked seats for a specific showtime.
     * 
     * @param showtimeId The ID of the showtime to retrieve the booked seats for.
     * @return A ResponseEntity containing the list of booked seats (SeatResponseDTO) with HTTP status 200 (OK),
     *         or HTTP status 404 (Not Found) if the showtime doesn't exist.
     */
    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<List<SeatResponseDTO>> getSeatsByShowtime(@PathVariable int showtimeId) {
        
        // Get the showtime by ID
        Optional<Showtime> optionalShowtime = showtimeService.getShowtimeById(showtimeId);

        if (optionalShowtime.isPresent()) {
            // Showtime exists, get the booked seats for this showtime
            List<Seat> seats = seatService.getSeatsByShowtime(optionalShowtime.get());

            // Convert the Seat entities to SeatResponseDTO for the response
            List<SeatResponseDTO> seatResponseList = seats.stream()
                    .map(seat -> new SeatResponseDTO(seat.getSeatNumber(), seat.getSeatRow()))
                    .collect(Collectors.toList());

            // Return the list of booked seats with HTTP status 200 (OK)
            return new ResponseEntity<>(seatResponseList, HttpStatus.OK);
        } else {
            // If the showtime doesn't exist, return HTTP status 404 (Not Found)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
