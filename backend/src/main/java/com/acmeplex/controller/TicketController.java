package com.acmeplex.controller;

import com.acmeplex.model.Ticket;
import com.acmeplex.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")  // Base URL for all ticket-related endpoints
public class TicketController {

    private final TicketService ticketService;

    // Constructor-based dependency injection for TicketService
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Get tickets by their status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Ticket>> getTicketsByStatus(@PathVariable String status) {
        List<Ticket> tickets = ticketService.getTicketsByStatus(status);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No tickets found with this status
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);  // Return the found tickets
    }

    // Get tickets by the showtime ID
    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<List<Ticket>> getTicketsByShowtime(@PathVariable Long showtimeId) {
        List<Ticket> tickets = ticketService.getTicketsByShowtime(showtimeId);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No tickets found for this showtime
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);  // Return the tickets for the showtime
    }

    // Get tickets by movie ID
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Ticket>> getTicketsByMovie(@PathVariable Long movieId) {
        List<Ticket> tickets = ticketService.getTicketsByMovie(movieId);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No tickets found for this movie
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);  // Return the tickets for the movie
    }

    // Get tickets by seat ID
    @GetMapping("/seat/{seatId}")
    public ResponseEntity<List<Ticket>> getTicketsBySeat(@PathVariable Long seatId) {
        List<Ticket> tickets = ticketService.getTicketsBySeat(seatId);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No tickets found for this seat
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);  // Return the tickets for the seat
    }

    // Get tickets purchased after a specific date
    @GetMapping("/purchaseAfter/{purchaseDate}")
    public ResponseEntity<List<Ticket>> getTicketsByPurchaseDateAfter(@PathVariable String purchaseDate) {
    // Define the expected date format
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Use the correct date format here
    try {
        // Parse the purchaseDate string into a Date object
        Date date = sdf.parse(purchaseDate);
        
        // Call the service method to get tickets after the specified date
        List<Ticket> tickets = ticketService.getTicketsByPurchaseDateAfter(date);
        
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No tickets found after this date
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);  // Return the tickets purchased after the specified date
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Return an error response if date parsing fails
    }
}

    // Get tickets within a specific price range
    @GetMapping("/priceRange/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Ticket>> getTicketsByPriceRange(@PathVariable double minPrice, @PathVariable double maxPrice) {
        List<Ticket> tickets = ticketService.getTicketsByPriceRange(minPrice, maxPrice);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No tickets found within this price range
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);  // Return the tickets within the price range
    }

    // Create a new ticket
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket savedTicket = ticketService.createTicket(ticket);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);  // Return the created ticket
    }

    // Update an existing ticket
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket updatedTicket) {
        Ticket ticket = ticketService.updateTicket(id, updatedTicket);
        if (ticket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Ticket not found
        }
        return new ResponseEntity<>(ticket, HttpStatus.OK);  // Return the updated ticket
    }

    // Delete a ticket by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return status 204 No Content (successful deletion)
    }
}
