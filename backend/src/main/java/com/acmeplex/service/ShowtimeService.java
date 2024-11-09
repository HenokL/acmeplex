package com.acmeplex.service;
import com.acmeplex.model.Showtime;
import com.acmeplex.model.Theater;
import com.acmeplex.model.Movie;
import com.acmeplex.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    // Get all showtimes after a specific start time
    public List<Showtime> getShowtimesAfterStartTime(LocalDateTime startTime) {
        return showtimeRepository.findByStartTimeAfter(startTime);
    }

    // Get all showtimes on a specific date
    public List<Showtime> getShowtimesByDate(LocalDate date) {
        return showtimeRepository.findByDate(date);
    }

    // Get all showtimes for a specific theater, movie, and date
    public List<Showtime> getShowtimesByTheaterMovieAndDate(Theater theater, Movie movie, LocalDate date) {
        return showtimeRepository.findByTheaterAndMovieAndDate(theater, movie, date);
    }

    // Get a specific showtime by its ID
    public Optional<Showtime> getShowtimeById(Long id) {
        return showtimeRepository.findById(id);
    }

    // Create or update a showtime
    public Showtime createOrUpdateShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }

    // Delete a showtime by its ID
    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }

    // Get all showtimes for a specific movie
    public List<Showtime> getShowtimesForMovie(Movie movie) {
        // Assume this method is supported by the repository or add a custom query if needed.
        return showtimeRepository.findByMovie(movie);
    }
}
