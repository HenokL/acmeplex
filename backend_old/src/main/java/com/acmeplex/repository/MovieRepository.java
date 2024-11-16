package com.acmeplex.repository;
import com.acmeplex.model.Movie; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Returns all available movies
    List<Movie> findAll();
}
