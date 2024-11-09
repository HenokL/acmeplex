package com.acmeplex.service;
import com.acmeplex.model.Movie;
import com.acmeplex.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // Method to retrieve all movies
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Method to get a movie by its ID
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    // Method to create a new movie
    public Movie createMovie(Movie movie) {
        // Example business logic: Check if the movie already exists by title (if needed)
        Optional<Movie> existingMovie = movieRepository.findById(movie.getId());
        if (existingMovie.isPresent()) {
            throw new IllegalArgumentException("Movie with this ID already exists.");
        }
        return movieRepository.save(movie);
    }

    // Method to update an existing movie
    public Movie updateMovie(Long id, Movie updatedMovie) {
        // Check if the movie exists
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (!existingMovie.isPresent()) {
            throw new IllegalArgumentException("Movie not found with ID: " + id);
        }

        // Update the movie fields and save it
        Movie movie = existingMovie.get();
        movie.setTitle(updatedMovie.getTitle());
        movie.setGenre(updatedMovie.getGenre());
        movie.setDirector(updatedMovie.getDirector());

        return movieRepository.save(movie);
    }

    // Method to delete a movie by ID
    public void deleteMovie(Long id) {
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (!existingMovie.isPresent()) {
            throw new IllegalArgumentException("Movie not found with ID: " + id);
        }

        movieRepository.deleteById(id);
    }
}
