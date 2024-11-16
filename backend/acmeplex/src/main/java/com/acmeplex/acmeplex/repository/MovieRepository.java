package com.acmeplex.repository;
import com.acmeplex.model.Movie; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    // Returns all available movies
    List<Movie> findAll();

    // Returns specific movie by ID
    Optional<Movie> findById(int id);
}
