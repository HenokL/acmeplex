package com.acmeplex.service;
import com.acmeplex.model.Payment;
import com.acmeplex.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Date;


@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Method to retrieve all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Method to retrieve a specific payment
    public Optional<Payment> getPaymentById(int id) {
        return paymentRepository.findById(id);
    }

    // Create a new payment
    public Payment createPayment(double amount, long creditCardNumber, String creditCardName, int creditCardCV) {
        // Create a new payment object
        Payment payment = new Payment(amount, creditCardNumber, creditCardName, creditCardCV, new Date());

        // Save the payment to the database
        return paymentRepository.save(payment);
    }

}
