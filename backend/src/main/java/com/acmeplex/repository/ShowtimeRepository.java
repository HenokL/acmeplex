package com.acmeplex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acmeplex.model.Showtime;
import com.acmeplex.model.Theater;
import com.acmeplex.model.Movie;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    
    // Find showtimes that start after a specified time
    List<Showtime> findByStartTimeAfter(LocalDateTime startTime);

    // Find showtimes by data
    List<Showtime> findByDate(LocalDate date);

    // Find showtimes by specific theater, movie and date
    List<Showtime> findByTheaterAndMovieAndDate(Theater theater, Movie movie, LocalDate date);

     // Find showtimes for a specific movie
     List<Showtime> findByMovie(Movie movie);
}
