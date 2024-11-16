package com.acmeplex.repository;
import com.acmeplex.model.Showtime;
import com.acmeplex.model.Ticket; 
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
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // Find tickets by email
    List<Ticket> findByEmail(String email);

    // Find tickets by showtime

    // Find showtimes for a specific movie and a specific start time
    List<Ticket> findByShowtime(Showtime showtime);
}
