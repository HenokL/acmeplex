package com.acmeplex.repository;
import com.acmeplex.model.RegisteredUser; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Integer> {

    // Returns all available users
    List<RegisteredUser> findAll();

    // Finds registered user by email
    Optional<RegisteredUser> findByEmail(String email);
}
