package com.acmeplex.controller;

import com.acmeplex.model.Ticket;
import com.acmeplex.model.Showtime;
import com.acmeplex.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    
    private final TicketService ticketService;

    // Constructor-based dependency injection
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Get ticket by its ID
    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        if (ticket != null) {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all tickets
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    // Cancelling a ticket for an ordinary user
    @PutMapping("/cancel/{ticketId}")
    public ResponseEntity<String> cancelTicket(@PathVariable int ticketId, @RequestBody Map<String, String> request) {

        // Fetching the ticket
        Ticket ticket = ticketService.getTicketById(ticketId);
        
        // Check if the ticket is found
        if (ticket == null) {
            return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
        }

          // Check if the ticket is already cancelled
        if (ticket.getStatus().equals("Cancelled")) {
            return new ResponseEntity<>("Ticket is already cancelled", HttpStatus.BAD_REQUEST);
        }

        // Getting the showtime
        Showtime showtime = ticket.getShowtime();

        // Getting the Date of the showtime
        java.sql.Date sqlShowtimeDate = showtime.getShowtimeDate();

         // Convert java.sql.Date to java.time.LocalDate
        LocalDate showtimeDate = sqlShowtimeDate.toLocalDate(); 

        // Getting the start time of the showtime
        String startTimeString = showtime.getStartTime();
        LocalTime showtimeStartTime = LocalTime.parse(startTimeString);

         // Combine the date and time into a full LocalDateTime
        LocalDateTime showtimeStart = LocalDateTime.of(showtimeDate, showtimeStartTime);

        // Getting the current time
        LocalDateTime now = LocalDateTime.now();

        // Calculating the time difference between now and the showtime
        Duration duration = Duration.between(now, showtimeStart);

        // Check if the difference is less or equal to 72 hours
        if (duration.toHours() <= 72) {
            return new ResponseEntity<>("Cannot cancel ticket within 72 hours of the showtime", HttpStatus.BAD_REQUEST);
        }

        // Get email from request body
        String email = request.get("email");

        // Check if the email is registered

        // Proceed to cancel the ticket if it is within the cancelable time frame
        ticketService.cancelTicket(ticketId, email);
        
        return new ResponseEntity<>("Ticket deleted successfully", HttpStatus.OK);
    }



    // Create a new ticket
    @PostMapping("/book")
    public ResponseEntity<String> bookTicket(@RequestBody Ticket ticketRequest) {

        // Extract ID from movie, seat and showtime
        int movieId = ticketRequest.getMovie().getMovieId();
        int seatId = ticketRequest.getSeat().getSeatId();
        int showtimeId = ticketRequest.getShowtime().getShowtimeId();

        // Save the ticket (this could also include checking if the seat is available)
        Ticket bookedTicket = ticketService.saveTicket(movieId, seatId, showtimeId);

        // Return a success message with HTTP status 201 (Created)
        return new ResponseEntity<>("Ticket booked successfully", HttpStatus.CREATED);
    }
}
