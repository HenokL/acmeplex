package com.acmeplex.repository;
import com.acmeplex.model.Showtime; 
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
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {

    // Find all showtimes for a specific movie
    List<Showtime> findByMovie(Movie movie);

    // Find showtimes for a specific movie and a specific start time
    List<Showtime> findByMovieAndStartTime(Movie movie, String startTime);

}
