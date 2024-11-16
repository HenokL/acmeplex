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

    // Method to retrieve a specific movie
    public Optional<Movie> getMovieById(int id) {
        return movieRepository.findById(id);
    }
}
