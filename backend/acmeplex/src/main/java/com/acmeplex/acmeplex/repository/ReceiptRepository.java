package com.acmeplex.repository;
import com.acmeplex.model.Receipt; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.List;             
import java.time.LocalTime;          
import javax.persistence.OneToMany;  
import javax.persistence.ManyToOne; 
import javax.persistence.JoinColumn; 
import com.acmeplex.model.Movie;   

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

    // Example: Find receipts by email
    List<Receipt> findByEmail(String email);

    // Example: Find receipts by payment ID
    List<Receipt> findByPayment_PaymentId(Integer paymentId);

    // Example: Find receipts by ticket ID
    List<Receipt> findByTicket_TicketId(Integer ticketId);

}
