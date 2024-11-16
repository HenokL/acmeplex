package com.acmeplex.repository;
import com.acmeplex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by their username
    Optional<User> findByUsername(String username);

    // Find a user by their email
    Optional<User> findByEmail(String email);

    // Find a user by their registration status
    List<User> findByIsRegistered(boolean isRegistered);
}
