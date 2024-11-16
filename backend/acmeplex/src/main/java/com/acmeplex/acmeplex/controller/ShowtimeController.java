package com.acmeplex.controller;
import com.acmeplex.model.Showtime;
import com.acmeplex.model.Movie;
import com.acmeplex.service.ShowtimeService;
import com.acmeplex.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ShowtimeController {

    
    private final ShowtimeService showtimeService;
    private final MovieService movieService;  



    // Constructor-based dependency injection
    public ShowtimeController(ShowtimeService showtimeService, MovieService movieService) {
        this.showtimeService = showtimeService;
        this.movieService = movieService; 
    }

    // Get showtimes for a specific movie
    @GetMapping("/movies/{movieId}/showtimes")
    public ResponseEntity<List<Showtime>> getShowtimesByMovie(@PathVariable int movieId) {

        // Get the movie instance
        Optional<Movie> movie = movieService.getMovieById(movieId);

        if (movie.isPresent()) {
            // Getting the showtimes for the movie
            List<Showtime> showtimes = showtimeService.getShowtimesByMovie(movie.get());
            
            // Sending the showtimes as a JSON
            return new ResponseEntity<>(showtimes, HttpStatus.OK);
        } else {
            // Did not find any showtimes for the movie
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);        }   
    }
}
