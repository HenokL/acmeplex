
package com.acmeplex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.acmeplex.model.Credit;
import java.util.List;


@Repository
public interface CreditRepository extends JpaRepository<Credit, Integer> {

    // Method to find all credits by email
    List<Credit> findAllByEmail(String email);
}