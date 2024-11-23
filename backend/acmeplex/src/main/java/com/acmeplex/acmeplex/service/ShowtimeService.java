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
import javax.persistence.OneToMany;  
import javax.persistence.ManyToOne; 
import javax.persistence.JoinColumn; 
import com.acmeplex.model.Movie;   

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    //  Get showtimes for a specific movie
    
    public List<ShowtimeDTO> getShowtimesByMovie(Movie movie) {
        List<Showtime> showtimes = showtimeRepository.findByMovie(movie);
        return showtimes.stream()
                .map(showtime -> new ShowtimeDTO(
                        showtime.getShowtimeId(),
                        showtime.getStartTime(),
                        showtime.getEndTime(),
                        showtime.getShowtimeDate()))
                .collect(Collectors.toList());
    }
    
    // Get showtimes for a movie at a specific start time
    public List<Showtime> getShowtimesByMovieAndStartTime(Movie movie, String startTime) {
        return showtimeRepository.findByMovieAndStartTime(movie, startTime);
    }

    // Get showtime by ID
    public Optional<Showtime> getShowtimeById(int showtimeId) {
        return showtimeRepository.findById(showtimeId);
    }
    
}
