package com.acmeplex.controller;

import com.acmeplex.model.Showtime;
import com.acmeplex.model.Movie;
import com.acmeplex.model.Theater;
import com.acmeplex.service.ShowtimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/showtimes")  // Base URL for all showtime-related endpoints
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    // Constructor-based dependency injection for ShowtimeService
    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    // Get all showtimes after a specific start time
    @GetMapping("/after/{startTime}")
    public ResponseEntity<List<Showtime>> getShowtimesAfterStartTime(@PathVariable LocalDateTime startTime) {
        List<Showtime> showtimes = showtimeService.getShowtimesAfterStartTime(startTime);
        if (showtimes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No showtimes found
        }
        return new ResponseEntity<>(showtimes, HttpStatus.OK);  // Return list of showtimes
    }

    // Get all showtimes on a specific date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Showtime>> getShowtimesByDate(@PathVariable LocalDate date) {
        List<Showtime> showtimes = showtimeService.getShowtimesByDate(date);
        if (showtimes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No showtimes found for this date
        }
        return new ResponseEntity<>(showtimes, HttpStatus.OK);  // Return showtimes for the specified date
    }

    // Get all showtimes for a specific theater, movie, and date
    @GetMapping("/theater/{theaterId}/movie/{movieId}/date/{date}")
    public ResponseEntity<List<Showtime>> getShowtimesByTheaterMovieAndDate(@PathVariable Long theaterId,
                                                                            @PathVariable Long movieId,
                                                                            @PathVariable LocalDate date) {
        // You would need to fetch the Theater and Movie objects based on their IDs
        Theater theater = new Theater();  // Assuming you can fetch the Theater by ID
        Movie movie = new Movie();  // Assuming you can fetch the Movie by ID
        List<Showtime> showtimes = showtimeService.getShowtimesByTheaterMovieAndDate(theater, movie, date);
        if (showtimes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No showtimes found for this combination
        }
        return new ResponseEntity<>(showtimes, HttpStatus.OK);  // Return the matching showtimes
    }

    // Get a specific showtime by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id) {
        Optional<Showtime> showtime = showtimeService.getShowtimeById(id);
        return showtime.map(s -> new ResponseEntity<>(s, HttpStatus.OK))  // Showtime found
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));  // Showtime not found
    }

    // Create or update a showtime
    @PostMapping
    public ResponseEntity<Showtime> createOrUpdateShowtime(@RequestBody Showtime showtime) {
        Showtime savedShowtime = showtimeService.createOrUpdateShowtime(showtime);
        return new ResponseEntity<>(savedShowtime, HttpStatus.CREATED);  // Return the created/updated showtime
    }

    // Delete a showtime by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return status 204 No Content (successful deletion)
    }

    // Get all showtimes for a specific movie
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Showtime>> getShowtimesForMovie(@PathVariable Long movieId) {
        Movie movie = new Movie();  // Assuming you can fetch the Movie by ID
        List<Showtime> showtimes = showtimeService.getShowtimesForMovie(movie);
        if (showtimes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No showtimes found for this movie
        }
        return new ResponseEntity<>(showtimes, HttpStatus.OK);  // Return showtimes for the movie
    }
}
