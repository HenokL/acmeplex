package com.acmeplex.service;

import com.acmeplex.model.Movie;
import com.acmeplex.dto.MovieDTO;
import com.acmeplex.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The MovieService class contains methods that provide business logic related to movies.
 * It interacts with the MovieRepository to retrieve and filter movies based on showtimes.
 * This service class also maps Movie entities to MovieDTO objects for presentation purposes.
 * Author: Riley Koppang
 */
@Service
public class MovieService {

    // Autowired MovieRepository allows access to the database for movie-related queries
    @Autowired
    private MovieRepository movieRepository;

    /**
     * Retrieves all movies that meet the following condition:
     * 1. Movies that have showtimes within the next 6 months.
     * 2. Movies with showtimes after 6 months.
     * 
     * @return A list of MovieDTOs representing filtered movies.
     */
    public List<MovieDTO> getAllMovies() {

        // Retrieve all movies from the database
        List<Movie> movies = movieRepository.findAll();

        // Calculate the date 6 months from the current date
        LocalDate sixMonthsFromNow = LocalDate.now().plusMonths(6);

        /*
         * Filter movies based on the condition:
         * 1. Movie has a showtime within the next 6 months.
         */
        List<Movie> filteredMovies = movies.stream()
            .filter(movie -> 
                // Condition 1: Movie has at least one showtime within the next 6 months
                movie.getShowtimes().stream()
                    .anyMatch(showtime -> showtime.getShowtimeDate().toLocalDate().isBefore(sixMonthsFromNow)))
            .collect(Collectors.toList());

        // Convert the filtered list of Movie entities to MovieDTOs for presentation
        return filteredMovies.stream()
                .map(movie -> new MovieDTO(
                        movie.getMovieId(),       // Movie ID
                        movie.getTitle(),         // Movie title
                        movie.getDuration(),      // Movie duration
                        movie.getGenre(),         // Movie genre
                        movie.getId()))           // Movie database ID
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific movie by its ID.
     * 
     * @param id The ID of the movie to retrieve.
     * @return An Optional containing the Movie if found, or empty if not.
     */
    public Optional<Movie> getMovieById(int id) {
        return movieRepository.findById(id);
    }

    /**
     * Retrieves all movies that are considered "upcoming."
     * "Upcoming" is defined as movies with showtimes later than 6 months from the current date.
     * 
     * @return A list of MovieDTOs representing upcoming movies.
     */
    public List<MovieDTO> getUpcomingMovies() {
        // Retrieve all movies from the database
        List<Movie> movies = movieRepository.findAll();
    
        // Calculate the date 6 months from the current date
        LocalDate sixMonthsFromNow = LocalDate.now().plusMonths(6);
    
        // Filter movies with showtimes later than 6 months
        List<Movie> upcomingMovies = movies.stream()
                .filter(movie -> movie.getShowtimes().stream()
                        .anyMatch(showtime -> showtime.getShowtimeDate().toLocalDate().isAfter(sixMonthsFromNow)))
                .collect(Collectors.toList());
        
        // Convert the filtered list of upcoming movies to MovieDTOs for presentation
        return upcomingMovies.stream()
                .map(movie -> new MovieDTO(
                        movie.getMovieId(),
                        movie.getTitle(),
                        movie.getDuration(),
                        movie.getGenre(),
                        movie.getId())) 
                .collect(Collectors.toList());
    }
}
