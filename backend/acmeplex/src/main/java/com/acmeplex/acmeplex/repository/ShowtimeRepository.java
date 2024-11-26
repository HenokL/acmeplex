package com.acmeplex.repository;

import com.acmeplex.model.Showtime;
import com.acmeplex.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * The ShowtimeRepository interface provides data access methods for interacting with the Showtime entity.
 * It extends JpaRepository, enabling standard CRUD operations and custom query methods to interact with the Showtime table.
 * Author: Riley Koppang
 */
@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {

    /**
     * Finds all showtimes for a specific movie.
     * This method retrieves all the showtimes associated with the specified movie.
     * 
     * @param movie The movie for which to find associated showtimes.
     * @return A list of Showtime entities associated with the specified movie.
     */
    List<Showtime> findByMovie(Movie movie);

    /**
     * Finds showtimes for a specific movie and a specific start time.
     * This method searches for showtimes associated with a given movie and start time.
     * 
     * @param movie The movie for which to find showtimes.
     * @param startTime The start time of the showtime to search for.
     * @return A list of Showtime entities that match the specified movie and start time.
     */
    List<Showtime> findByMovieAndStartTime(Movie movie, String startTime);
}
