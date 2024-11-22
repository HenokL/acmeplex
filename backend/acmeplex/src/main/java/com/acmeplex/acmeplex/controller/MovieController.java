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
@RequestMapping("/api")
public class MovieController {

    
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Retrieves a list of all movies
    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
         // Get all movies from the service
         List<Movie> movies = movieService.getAllMovies();
         
        // Return the list of movies with HTTP status 200 (OK)
        return ResponseEntity.ok(movies); 
    }

    // Retrieves information about a specific movie
    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") int id) {
        Optional<Movie> movie = movieService.getMovieById(id); 

        if (movie.isPresent()) {
            // Return the movie with OK if found
            return new ResponseEntity<>(movie.get(), HttpStatus.OK);  
        } else {
            //  Return 404 not found if the movie does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        }
    }    
}
