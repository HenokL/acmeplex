package com.acmeplex.repository;
import com.acmeplex.model.Showtime;
import com.acmeplex.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.List;             
import java.time.LocalTime;          
import javax.persistence.OneToMany;  
import javax.persistence.ManyToOne; 
import javax.persistence.JoinColumn; 
import com.acmeplex.model.Movie;   

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    // Find all showtimes for a specific movie
    List<Seat> findByShowtime(Showtime showtime);

     // Find a specific seat by seatNumber and showtime
     Optional<Seat> findBySeatNumberAndShowtime(int seatNumber, Showtime showtime);
}
