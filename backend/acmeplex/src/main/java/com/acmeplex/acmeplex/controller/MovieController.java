package com.acmeplex.controller;

import com.acmeplex.model.Movie;
import com.acmeplex.service.MovieService;
import com.acmeplex.dto.MovieDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

/**
 * MovieController handles HTTP requests related to movie data.
 * It includes endpoints to retrieve all movies, details for a specific movie,
 * and a list of upcoming movies.
 * Author: Riley Koppang
 */
@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    // Constructor to inject MovieService
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Retrieves a list of all movies.
     * 
     * @return A ResponseEntity containing the list of all movies with HTTP status 200 (OK).
     */
    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
         // Get all movies from the service
         List<MovieDTO> movies = movieService.getAllMovies();

        // Return the list of movies with HTTP status 200 (OK)
        return ResponseEntity.ok(movies); 
    }

    /**
     * Retrieves information about a specific movie by its ID.
     * 
     * @param id The ID of the movie.
     * @return A ResponseEntity containing the movie data if found, or HTTP status 404 (Not Found) if not found.
     */
    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") int id) {
        Optional<Movie> movie = movieService.getMovieById(id);

        if (movie.isPresent()) {
            // Return the movie with OK status if found
            return new ResponseEntity<>(movie.get(), HttpStatus.OK);
        } else {
            // Return HTTP status 404 (Not Found) if the movie does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a list of upcoming movies.
     * 
     * @return A ResponseEntity containing the list of upcoming movies with HTTP status 200 (OK).
     */
    @GetMapping("/upcoming/movies")
    public ResponseEntity<List<MovieDTO>> getUpcomingMovies() {

         // Get all upcoming movies
         List<MovieDTO> upcomingMovies = movieService.getUpcomingMovies();
         
        // Return the list of upcoming movies with HTTP status 200 (OK)
        return ResponseEntity.ok(upcomingMovies); 
    }

}
