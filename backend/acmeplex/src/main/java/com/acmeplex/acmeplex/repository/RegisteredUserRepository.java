package com.acmeplex.repository;

import com.acmeplex.model.RegisteredUser; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * The RegisteredUserRepository interface provides data access methods for interacting with the RegisteredUser entity.
 * It extends JpaRepository, allowing for standard CRUD operations as well as custom query methods to interact with the RegisteredUser table.
 * Author: Riley Koppang
 */
@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Integer> {

    /**
     * Retrieves all registered users from the database.
     * This method returns a list of all users present in the `RegisteredUser` table.
     * 
     * @return A list of all RegisteredUser entities.
     */
    List<RegisteredUser> findAll();

    /**
     * Finds a registered user by their email address.
     * This method searches for a user based on the provided email and returns an Optional containing the user if found.
     * 
     * @param email The email address of the user to search for.
     * @return An Optional containing the RegisteredUser entity, or empty if no user is found with the given email.
     */
    Optional<RegisteredUser> findByEmail(String email);

    /**
     * Checks if a registered user exists with the specified email address.
     * This method returns true if there is a user with the given email, and false otherwise.
     * 
     * @param email The email address to check for existence.
     * @return true if a user exists with the given email, false otherwise.
     */
    boolean existsByEmail(String email);
}
