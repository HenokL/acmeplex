package com.acmeplex.repository;
import com.acmeplex.model.Movie; 
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Returns all available movies
    List<Movie> findAll();
}
