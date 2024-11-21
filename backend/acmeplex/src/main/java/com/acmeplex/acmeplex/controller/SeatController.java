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
    
    // Book a seat for a showtime
    @PostMapping("/book")
    public ResponseEntity<String> bookSeat(@RequestBody Seat seatRequest) {
        // Retrieve the showtime by ID
        int showtimeId = seatRequest.getShowtime().getShowtimeId(); 

        Optional<Showtime> optionalShowtime = showtimeService.getShowtimeById(showtimeId);
        if (!optionalShowtime.isPresent()) {
            // If showtime does not exist, return NOT_FOUND response
            return new ResponseEntity<>("Showtime not found", HttpStatus.NOT_FOUND);
        }

        Showtime showtime = optionalShowtime.get();

        // Check if this seat has already been booked for this showtime
        Optional<Seat> existingSeat = seatService.getSeatByNumberAndShowtime(seatRequest.getSeatNumber(), showtime);
        if (existingSeat.isPresent()) {
            // If the seat exists, it means it's already booked
            return new ResponseEntity<>("Seat is already booked", HttpStatus.BAD_REQUEST);
        }

        // Save the seat as booked by associating it with the showtime
        Seat bookedSeat = new Seat(seatRequest.getSeatNumber(), seatRequest.getSeatRow(), showtime);
        seatService.saveSeat(bookedSeat);

        // Return success message with status CREATED
        return new ResponseEntity<>("Seat booked successfully", HttpStatus.CREATED);
    }

}
