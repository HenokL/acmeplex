package com.acmeplex.controller;
import com.acmeplex.model.Movie;
import com.acmeplex.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apiddddd/movies")  // Base URL for the movie-related API endpoints
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Returns all movies for endpoint "movies"
    @GetMapping
    public ResponseEntity<Map<String, List<Movie>>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();

        // Converting movies into JSON format
        Map<String, List<Movie>> response = new HashMap<>();
        response.put("movies", movies);
        return new ResponseEntity<>(response, HttpStatus.OK); // Return movies with status 200 OK
    }
    

    // Get a movie by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        if (movie.isPresent()) {
            return new ResponseEntity<>(movie.get(), HttpStatus.OK);  // Movie found
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Movie not found
        }
    }

    // Create a new movie
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        try {
            Movie createdMovie = movieService.createMovie(movie);
            return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);  // Return the created movie with status 201 CREATED
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // If the movie already exists, return 400 Bad Request
        }
    }

    // Update an existing movie
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        try {
            Movie movie = movieService.updateMovie(id, updatedMovie);
            return new ResponseEntity<>(movie, HttpStatus.OK);  // Return the updated movie with status 200 OK
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // If the movie is not found, return 404 Not Found
        }
    }

    // Delete a movie by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        try {
            movieService.deleteMovie(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return status 204 No Content (successful deletion)
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // If the movie is not found, return 404 Not Found
        }
    }
}
