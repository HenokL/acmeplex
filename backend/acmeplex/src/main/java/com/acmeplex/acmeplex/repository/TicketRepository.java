package com.acmeplex.repository;

import com.acmeplex.model.Showtime;
import com.acmeplex.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * The TicketRepository interface provides data access methods for interacting with the Ticket entity.
 * It extends JpaRepository, enabling standard CRUD operations and custom query methods to interact with the Ticket table.
 * Author: Riley Koppang
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    /**
     * Finds all tickets associated with a specific email address.
     * This method retrieves a list of tickets that belong to the user identified by the provided email.
     * 
     * @param email The email address of the user whose tickets are being retrieved.
     * @return A list of Ticket entities associated with the given email.
     */
    List<Ticket> findByEmail(String email);

    /**
     * Finds all tickets for a specific showtime.
     * This method retrieves a list of tickets that are associated with a particular showtime.
     * 
     * @param showtime The showtime for which tickets are being retrieved.
     * @return A list of Ticket entities associated with the specified showtime.
     */
    List<Ticket> findByShowtime(Showtime showtime);
}
