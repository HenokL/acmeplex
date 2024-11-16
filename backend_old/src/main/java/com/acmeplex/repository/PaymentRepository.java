package com.acmeplex.repository;
import com.acmeplex.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Custom query to find payments by payer name
    List<Payment> findByPayerName(String payerName);

    // Custom query to find payments made with a specific method
    List<Payment> findByPaymentMethod(String paymentMethod);

    // Custom query to find payments greater than or equal to a certain amount
    List<Payment> findByAmountGreaterThanEqual(double amount);
    
    // Returns all of the payments
    List<Payment> findAll();
}
