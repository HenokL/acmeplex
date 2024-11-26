package com.acmeplex.service;

import com.acmeplex.model.Showtime;
import com.acmeplex.repository.ShowtimeRepository;
import com.acmeplex.dto.ShowtimeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalTime;
import com.acmeplex.model.Movie;

/**
 * The ShowtimeService class provides business logic related to movie showtimes.
 * It interacts with the ShowtimeRepository to retrieve, filter, and map showtime data.
 * Author: Riley Koppang
 */
@Service
public class ShowtimeService {

    // Injecting the ShowtimeRepository to interact with the database
    @Autowired
    private ShowtimeRepository showtimeRepository;

    /**
     * Retrieves a list of showtimes for a specific movie.
     * 
     * @param movie The Movie object for which the showtimes are to be retrieved.
     * @return A list of ShowtimeDTO objects, each representing a showtime for the provided movie.
     */
    public List<ShowtimeDTO> getShowtimesByMovie(Movie movie) {
        // Retrieves all showtimes for the given movie from the repository
        List<Showtime> showtimes = showtimeRepository.findByMovie(movie);
        
        // Maps the Showtime objects to ShowtimeDTO objects
        return showtimes.stream()
                .map(showtime -> new ShowtimeDTO(
                        showtime.getShowtimeId(),
                        showtime.getStartTime(),
                        showtime.getEndTime(),
                        showtime.getShowtimeDate()))
                .collect(Collectors.toList()); // Collect the results into a list
    }
    
    /**
     * Retrieves a list of showtimes for a specific movie at a specific start time.
     * 
     * @param movie The Movie object for which the showtimes are to be retrieved.
     * @param startTime The start time of the showtimes to retrieve.
     * @return A list of Showtime objects for the given movie and start time.
     */
    public List<Showtime> getShowtimesByMovieAndStartTime(Movie movie, String startTime) {
        // Uses the repository's findByMovieAndStartTime() method to retrieve showtimes for the given movie and start time
        return showtimeRepository.findByMovieAndStartTime(movie, startTime);
    }

    /**
     * Retrieves a specific showtime by its ID.
     * 
     * @param showtimeId The ID of the showtime to retrieve.
     * @return An Optional containing the Showtime object if found, otherwise empty.
     */
    public Optional<Showtime> getShowtimeById(int showtimeId) {
        // Uses the repository's findById() method to retrieve a showtime by its ID
        return showtimeRepository.findById(showtimeId);
    }
}
