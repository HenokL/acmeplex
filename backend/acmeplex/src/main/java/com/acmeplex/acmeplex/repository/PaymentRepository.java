package com.acmeplex.repository;

import com.acmeplex.model.Payment; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * The PaymentRepository interface is used for data access operations related to the Payment entity.
 * It extends JpaRepository, providing standard CRUD operations and additional querying capabilities.
 * This interface allows interaction with the Payment table in the database.
 * Author: Riley Koppang
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    /**
     * Retrieves all payments from the database.
     * This method is automatically implemented by Spring Data JPA based on the method name.
     *
     * @return A list of all available Payment entities.
     */
    List<Payment> findAll();

    /**
     * Finds a specific payment by its ID.
     * This method returns an Optional to handle the case where a payment with the given ID may not exist.
     *
     * @param id The ID of the payment to retrieve.
     * @return An Optional containing the Payment entity if found, or empty if not.
     */
    Optional<Payment> findById(int id);
}
