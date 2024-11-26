package com.acmeplex.repository;

import com.acmeplex.model.Receipt; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.LocalTime;  
import com.acmeplex.model.Movie;   

/**
 * The ReceiptRepository interface is used for data access operations related to the Receipt entity.
 * It extends JpaRepository, providing standard CRUD operations and custom query methods to interact with the Receipt table in the database.
 * Author: Riley Koppang
 */
@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

    /**
     * Finds all receipts associated with a specific email address.
     * This method allows you to retrieve all receipts for a given user based on their email.
     * 
     * @param email The email address to search for.
     * @return A list of Receipt entities associated with the given email.
     */
    List<Receipt> findByEmail(String email);

    /**
     * Finds all receipts associated with a specific payment ID.
     * This method is used to retrieve all receipts linked to a particular payment.
     * 
     * @param paymentId The payment ID to search for.
     * @return A list of Receipt entities associated with the given payment ID.
     */
    List<Receipt> findByPayment_PaymentId(Integer paymentId);

    /**
     * Finds all receipts associated with a specific ticket ID.
     * This method is used to retrieve all receipts linked to a particular ticket.
     * 
     * @param ticketId The ticket ID to search for.
     * @return A list of Receipt entities associated with the given ticket ID.
     */
    List<Receipt> findByTicket_TicketId(Integer ticketId);
}
