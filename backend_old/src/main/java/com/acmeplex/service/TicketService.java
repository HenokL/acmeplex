package com.acmeplex.service;
import com.acmeplex.model.Ticket;
import com.acmeplex.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    // Constructor injection of the TicketRepository
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Get tickets by their status
    public List<Ticket> getTicketsByStatus(String status) {
        return ticketRepository.findByStatus(status);
    }

    // Get tickets by the showtime ID
    public List<Ticket> getTicketsByShowtime(Long showtimeId) {
        return ticketRepository.findByShowtimeId(showtimeId);
    }

    // Get tickets by movie ID
    public List<Ticket> getTicketsByMovie(Long movieId) {
        return ticketRepository.findByMovieId(movieId);
    }

    // Get tickets by seat ID
    public List<Ticket> getTicketsBySeat(Long seatId) {
        return ticketRepository.findBySeatId(seatId);
    }

    // Get tickets purchased after a specific date
    public List<Ticket> getTicketsByPurchaseDateAfter(Date date) {
        return ticketRepository.findByPurchaseDateAfter(date);
    }

    // Get tickets within a specific price range
    public List<Ticket> getTicketsByPriceRange(double minPrice, double maxPrice) {
        return ticketRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // Create a new ticket
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);  // Save the new ticket to the database
    }

    // Update an existing ticket
    public Ticket updateTicket(Long ticketId, Ticket updatedTicket) {
        Optional<Ticket> existingTicket = ticketRepository.findById(ticketId);
        if (existingTicket.isPresent()) {
            Ticket ticket = existingTicket.get();
            ticket.setStatus(updatedTicket.getStatus());
            ticket.setPrice(updatedTicket.getPrice());
            ticket.setPurchaseDate(updatedTicket.getPurchaseDate());
            ticket.setShowtime(updatedTicket.getShowtime());
            ticket.setSeat(updatedTicket.getSeat());
            ticket.setMovie(updatedTicket.getMovie());
            return ticketRepository.save(ticket);  // Save the updated ticket
        }
        return null;  // Return null or throw an exception if ticket is not found
    }

    // Delete a ticket
    public void deleteTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);  // Delete the ticket by ID
    }
}
