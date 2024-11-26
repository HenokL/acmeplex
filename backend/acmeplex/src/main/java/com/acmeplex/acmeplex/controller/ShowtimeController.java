package com.acmeplex.controller;

import com.acmeplex.model.Showtime;
import com.acmeplex.model.Movie;
import com.acmeplex.service.ShowtimeService;
import com.acmeplex.service.MovieService;
import com.acmeplex.dto.ShowtimeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

/**
 * ShowtimeController handles HTTP requests related to showtimes for movies.
 * It includes functionality to retrieve all showtimes for a specific movie.
 * Author: Riley Koppang
 */
@RestController
@RequestMapping("/api")
public class ShowtimeController {

    private final ShowtimeService showtimeService;
    private final MovieService movieService;

    /**
     * Constructor-based dependency injection for ShowtimeService and MovieService.
     * 
     * @param showtimeService The service handling showtime-related operations.
     * @param movieService The service handling movie-related operations.
     */
    public ShowtimeController(ShowtimeService showtimeService, MovieService movieService) {
        this.showtimeService = showtimeService;
        this.movieService = movieService;
    }

    /**
     * Retrieves all showtimes for a specific movie.
     * 
     * @param movieId The ID of the movie for which showtimes need to be retrieved.
     * @return A ResponseEntity containing the list of showtimes (ShowtimeDTO) with HTTP status 200 (OK),
     *         or HTTP status 404 (Not Found) if the movie doesn't exist.
     */
    @GetMapping("/movies/{movieId}/showtimes")
    public ResponseEntity<List<ShowtimeDTO>> getShowtimesByMovie(@PathVariable int movieId) {

        // Get the movie by its ID
        Optional<Movie> movie = movieService.getMovieById(movieId);

        if (movie.isPresent()) {
            // Movie exists, get the showtimes for this movie
            List<ShowtimeDTO> showtimes = showtimeService.getShowtimesByMovie(movie.get());

            // Return the list of showtimes as a JSON response with HTTP status 200 (OK)
            return new ResponseEntity<>(showtimes, HttpStatus.OK);
        } else {
            // Movie not found, return HTTP status 404 (Not Found)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
