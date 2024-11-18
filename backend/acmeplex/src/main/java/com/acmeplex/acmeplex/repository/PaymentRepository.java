package com.acmeplex.repository;
import com.acmeplex.model.Payment; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    // Returns all available movies
    List<Payment> findAll();

    // Returns specific movie by ID
    Optional<Payment> findById(int id);
}
