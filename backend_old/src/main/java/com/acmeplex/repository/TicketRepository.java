package com.acmeplex.repository;
import com.acmeplex.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Find tickets by their status
    List<Ticket> findByStatus(String status);

    // Find tickets by the showtime
    List<Ticket> findByShowtimeId(Long showtimeId);

    // Find tickets by movie
    List<Ticket> findByMovieId(Long movieId);

    // Find tickets by seat
    List<Ticket> findBySeatId(Long seatId);

    // Find tickets by purchase date (e.g., all tickets purchased after a certain date)
    List<Ticket> findByPurchaseDateAfter(Date date);

    // Find tickets by price range
    List<Ticket> findByPriceBetween(double minPrice, double maxPrice);
}
