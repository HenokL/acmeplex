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
import java.time.LocalTime;          
import javax.persistence.OneToMany;  
import javax.persistence.ManyToOne; 
import javax.persistence.JoinColumn; 
import com.acmeplex.model.Movie;
import com.acmeplex.model.Seat;
import com.acmeplex.model.Showtime;   
import com.acmeplex.exception.EntityNotFoundException;


@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private CreditService creditService;

    @Autowired
    private RegisteredUserService registeredUserService;


    // Save a ticket
    public Ticket saveTicket(int movieId, int seatId, int showtimeId) {
        // Retrieve the existing Movie, Seat, and Showtime from the database
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    
        Seat seat = seatRepository.findById(seatId)
            .orElseThrow(() -> new IllegalArgumentException("Seat not found"));
    
        Showtime showtime = showtimeRepository.findById(showtimeId)
            .orElseThrow(() -> new IllegalArgumentException("Showtime not found"));
    
        // Create a new Ticket and set its associations
        Ticket ticket = new Ticket();
        ticket.setMovie(movie); 
        ticket.setSeat(seat);   
        ticket.setShowtime(showtime);
    
        // Save the Ticket to the repository
        return ticketRepository.save(ticket); 
    }

    // Find ticket by ID
    public Ticket getTicketById(int ticketId) {
        return ticketRepository.findById(ticketId).orElse(null);
    }

    // Find all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Delete a ticket
    public void deleteTicket(int ticketId) {
        ticketRepository.deleteById(ticketId);
    }
    

    /*
     * Cancel a ticket
     * Update its status from Booked to Cancelled
     */
    public void cancelTicket(int ticketId, String email) {
        
        // Attempt to retrieve the ticket
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        // Update its status to "Cancelled"
        ticket.setStatus("Cancelled");

        // Save the updated ticket
        ticketRepository.save(ticket);

         // Check if the email is registered
         boolean isEmailRegistered = registeredUserService.isEmailRegistered(email);

        // Refund logic: Registered users get 100%, non-registered users get 85%
        double refundAmount = ticket.getPrice();
        if (!isEmailRegistered) {
            refundAmount *= 0.85;  // Apply 85% refund if the email is not registered
        }

        // Issue the refund to the user (assuming creditService has a method for this)
        creditService.issueCredits(email, refundAmount);
    }
}
