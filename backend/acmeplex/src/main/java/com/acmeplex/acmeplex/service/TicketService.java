package com.acmeplex.service;
import com.acmeplex.model.Ticket;
import com.acmeplex.repository.TicketRepository;
import com.acmeplex.repository.MovieRepository;
import com.acmeplex.repository.SeatRepository;
import com.acmeplex.repository.ShowtimeRepository;
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
}
