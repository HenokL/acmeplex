package com.acmeplex.service;

import com.acmeplex.model.Ticket;
import com.acmeplex.repository.TicketRepository;
import com.acmeplex.repository.MovieRepository;
import com.acmeplex.repository.SeatRepository;
import com.acmeplex.repository.ShowtimeRepository;
import com.acmeplex.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import com.acmeplex.model.Movie;
import com.acmeplex.model.Seat;
import com.acmeplex.model.Showtime;
import com.acmeplex.exception.EntityNotFoundException;
import com.acmeplex.service.CreditService;
import com.acmeplex.service.RegisteredUserService;

/**
 * The TicketService class provides business logic related to movie tickets,
 * including creating, retrieving, deleting, and cancelling tickets.
 * It interacts with repositories for tickets, movies, showtimes, seats, and users.
 * Author: Riley Koppang
 */
@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;  // Repository for ticket operations

    @Autowired
    private MovieRepository movieRepository;  // Repository for movie operations

    @Autowired
    private SeatRepository seatRepository;  // Repository for seat operations

    @Autowired
    private ShowtimeRepository showtimeRepository;  // Repository for showtime operations

    @Autowired
    private RegisteredUserRepository registeredUserRepository;  // Repository for registered users

    @Autowired
    private CreditService creditService;  // Service for managing credits

    @Autowired
    private RegisteredUserService registeredUserService;  // Service for handling registered user operations

    /**
     * Saves a new ticket by associating it with a movie, showtime, seats, and user email.
     * 
     * @param movieId The ID of the movie being booked.
     * @param seats A list of Seat objects being booked.
     * @param showtimeId The ID of the showtime for the movie.
     * @param email The email address of the user booking the ticket.
     * @param price The price of the ticket.
     * @return The saved Ticket object.
     * @throws IllegalArgumentException If the movie or showtime cannot be found or if the seats don't match the showtime.
     */
    public Ticket saveTicket(int movieId, List<Seat> seats, int showtimeId, String email, double price) {
        // Retrieve the movie and showtime from the database
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        
        Showtime showtime = showtimeRepository.findById(showtimeId)
            .orElseThrow(() -> new IllegalArgumentException("Showtime not found"));

        // Validate that each seat belongs to the specified showtime
        for (Seat seat : seats) {
            if (seat.getShowtime().getShowtimeId() != showtimeId) {
                throw new IllegalArgumentException("Seat " + seat.getSeatRow() + "-" + seat.getSeatNumber() 
                    + " does not belong to showtime " + showtimeId);
            }
        }

        // Create the ticket and associate it with the movie, showtime, and seats
        Ticket ticket = new Ticket();
        ticket.setMovie(movie);
        ticket.setShowtime(showtime);
        ticket.setPrice(price);
        ticket.setEmail(email);
        ticket.setStatus("Booked");
        ticket.setPurchaseDate(new Date());

        // Set bidirectional relationship between ticket and seats
        for (Seat seat : seats) {
            seat.setTicket(ticket); 
        }
        ticket.setSeats(seats);

        // Save the ticket in the database and return it
        return ticketRepository.save(ticket);
    }

    /**
     * Retrieves a ticket by its ID.
     * 
     * @param ticketId The ID of the ticket to retrieve.
     * @return The Ticket object if found, or null if not.
     */
    public Ticket getTicketById(int ticketId) {
        return ticketRepository.findById(ticketId).orElse(null);
    }

    /**
     * Retrieves all tickets from the database.
     * 
     * @return A list of all Ticket objects.
     */
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    /**
     * Deletes a ticket by its ID.
     * 
     * @param ticketId The ID of the ticket to delete.
     */
    public void deleteTicket(int ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    /**
     * Cancels a ticket by updating its status to "Cancelled" and deleting the associated seats.
     * Also processes a refund based on whether the user is registered.
     * 
     * @param ticketId The ID of the ticket to cancel.
     * @param email The email address of the user requesting the cancellation.
     * @throws EntityNotFoundException If the ticket cannot be found.
     */
    public void cancelTicket(int ticketId, String email) {
        // Attempt to retrieve the ticket by ID
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        // Retrieve and delete associated seats from the database
        List<Seat> seatsToDelete = ticket.getSeats();
        seatRepository.deleteAll(seatsToDelete);

        // Update the ticket status to "Cancelled"
        ticket.setStatus("Cancelled");

        // Save the updated ticket
        ticketRepository.save(ticket);

        // Check if the email is registered
        boolean isEmailRegistered = registeredUserService.isEmailRegistered(email);

        // Refund logic: Registered users get 100%, non-registered users get 85%
        double refundAmount = ticket.getPrice();
        if (!isEmailRegistered) {
            refundAmount *= 0.85;  // Apply 85% refund for non-registered users
        }

        // Issue the refund to the user via the CreditService
        creditService.issueCredits(email, refundAmount);
    }
}
