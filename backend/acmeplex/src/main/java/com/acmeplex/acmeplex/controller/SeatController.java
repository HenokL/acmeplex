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
}
