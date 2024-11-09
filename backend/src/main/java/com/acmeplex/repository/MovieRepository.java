package com.acmeplex.repository;
import com.acmeplex.model.Movie; 
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByDirector(String director);

    List<Movie> findByRatingGreaterThanEqual(double rating);
}
