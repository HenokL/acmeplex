package com.acmeplex.repository;

import com.acmeplex.model.Movie; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * The MovieRepository interface is used for data access operations related to the Movie entity.
 * It extends JpaRepository, which provides CRUD operations and additional querying capabilities.
 * This interface allows interaction with the Movie table in the database.
 * Author: Riley Koppang
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    /**
     * Retrieves all movies from the database.
     * This method is automatically implemented by Spring Data JPA based on the method name.
     *
     * @return A list of all available Movie entities.
     */
    List<Movie> findAll();

    /**
     * Finds a specific movie by its ID.
     * This method returns an Optional to handle the case where a movie with the given ID may not exist.
     *
     * @param id The ID of the movie to retrieve.
     * @return An Optional containing the Movie entity if found, or empty if not.
     */
    Optional<Movie> findById(int id);
}
