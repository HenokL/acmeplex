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


@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // Method to retrieve all movies
    public List<MovieDTO> getAllMovies() {

        // Retrieving all the movies
        List<Movie> movies = movieRepository.findAll();

        // Calculating the date six months from now
        LocalDate sixMonthsFromNow = LocalDate.now().plusMonths(6);

        /*
         * CONDITIONAL
         * Retrieve movies that have a showtime within 6 months
         * OR
         * Movies that have showtimes after 6 months with at least 4 seats filled
         */
        List<Movie> filteredMovies = movies.stream()
            .filter(movie -> 
                // Condition 1: Movie has a showtime within the next 6 months
                movie.getShowtimes().stream()
                    .anyMatch(showtime -> showtime.getShowtimeDate().toLocalDate().isBefore(sixMonthsFromNow) || 
                                          // Condition 2: Movie has showtimes after 6 months with at least 4 seats filled
                                          (showtime.getShowtimeDate().toLocalDate().isAfter(sixMonthsFromNow) && 
                                           showtime.getBookedSeats() >= 4))
            )
            .collect(Collectors.toList());

        // Map movie entities to MovieDTOs
        return filteredMovies.stream()
                .map(movie -> new MovieDTO(
                        movie.getMovieId(),
                        movie.getTitle(),
                        movie.getDuration(),
                        movie.getGenre()))
                .collect(Collectors.toList());
    }
    
    // Method to retrieve a specific movie
    public Optional<Movie> getMovieById(int id) {
        return movieRepository.findById(id);
    }

    /*
     * Method to retreive all upcoming movies
     * Upcoming is defined as movies released later than 6 months from now
     */
    public List<MovieDTO> getUpcomingMovies() {
        // Retrieve all movies
        List<Movie> movies = movieRepository.findAll();
    
        // Calculating the date six months from now
        LocalDate sixMonthsFromNow = LocalDate.now().plusMonths(6);
    
        // Only include those movies with showtimes later than 6 months
        List<Movie> upcomingMovies = movies.stream()
                .filter(movie -> movie.getShowtimes().stream()
                        .anyMatch(showtime -> showtime.getShowtimeDate().toLocalDate().isAfter(sixMonthsFromNow)))
                .collect(Collectors.toList());
        
        // Convert movies to MovieDTO
        return upcomingMovies.stream()
                .map(movie -> new MovieDTO(
                        movie.getMovieId(),
                        movie.getTitle(),
                        movie.getDuration(),
                        movie.getGenre())) 
                .collect(Collectors.toList());
    }
}
