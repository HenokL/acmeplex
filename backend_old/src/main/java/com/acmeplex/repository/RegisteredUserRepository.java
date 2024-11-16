package com.acmeplex.repository;
import com.acmeplex.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {

    // Find a RegisteredUser by their username (inherited from User)
    Optional<RegisteredUser> findByUsername(String username);

    // Find a RegisteredUser by their email (inherited from User)
    Optional<RegisteredUser> findByEmail(String email);

    // Find RegisteredUsers by their membershipExpiryDate
    List<RegisteredUser> findByMembershipExpiryDateBefore(Date date);

    // Find RegisteredUsers by discountRate
    List<RegisteredUser> findByDiscountRate(int discountRate);

    // Additional custom queries can be added using the @Query annotation if needed
}
