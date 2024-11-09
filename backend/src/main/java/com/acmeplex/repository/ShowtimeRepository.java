package com.acmeplex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acmeplex.model.Showtime;
import com.acmeplex.model.Theater;
import com.acmeplex.model.Movie;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    
    // Example queries
    List<Showtime> findByStartTimeAfter(LocalDateTime startTime);

    List<Showtime> findByDate(LocalDate date);

    List<Showtime> findByTheaterAndMovieAndDate(Theater theater, Movie movie, LocalDate date);
}
