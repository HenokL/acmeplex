package com.acmeplex.controller;
import com.acmeplex.model.Ticket;
import com.acmeplex.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
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

    // Delete a ticket
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<String> deleteTicket(@PathVariable int ticketId) {
        ticketService.deleteTicket(ticketId);
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
