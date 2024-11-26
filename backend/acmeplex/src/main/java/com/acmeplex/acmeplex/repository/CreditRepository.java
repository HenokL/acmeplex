package com.acmeplex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.acmeplex.model.Credit;
import java.util.List;

/**
 * The CreditRepository interface is used for data access operations related to the Credit entity.
 * It extends JpaRepository to provide CRUD operations on the Credit table.
 * This interface uses Spring Data JPA to manage data persistence without the need for boilerplate code.
 * Author: Riley Koppang
 */
@Repository
public interface CreditRepository extends JpaRepository<Credit, Integer> {

    /**
     * Finds all credits associated with a given email address.
     * This method is derived from the method name and automatically implemented by Spring Data JPA.
     *
     * @param email The email address used to find the associated credits.
     * @return A list of Credit entities linked to the specified email address.
     */
    List<Credit> findAllByEmail(String email);
}
