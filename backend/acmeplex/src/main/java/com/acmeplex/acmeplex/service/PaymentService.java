package com.acmeplex.service;

import com.acmeplex.model.Payment;
import com.acmeplex.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

/**
 * The PaymentService class contains methods that provide business logic related to payments.
 * It interacts with the PaymentRepository to retrieve, create, and manage payments.
 * Author: Riley Koppang
 */
@Service
public class PaymentService {

    // Autowired PaymentRepository allows access to the database for payment-related queries
    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * Retrieves all payments from the database.
     * 
     * @return A list of all Payment objects.
     */
    public List<Payment> getAllPayments() {
        // Returns all payments by calling the repository's findAll() method
        return paymentRepository.findAll();
    }

    /**
     * Retrieves a specific payment based on its ID.
     * 
     * @param id The ID of the payment to retrieve.
     * @return An Optional containing the Payment if found, or empty if not.
     */
    public Optional<Payment> getPaymentById(int id) {
        // Finds a payment by its ID using the repository's findById() method
        return paymentRepository.findById(id);
    }

    /**
     * Creates a new payment record and saves it to the database.
     * The total amount is the sum of the payment amount and any credits used.
     * 
     * @param amount The total amount for the payment.
     * @param creditCardNumber The credit card number for the payment.
     * @param creditCardName The name on the credit card.
     * @param creditCardCV The CVV of the credit card.
     * @param creditsUsed The amount of credits used during the payment.
     * @return The saved Payment object.
     */
    public Payment createPayment(double amount, String creditCardNumber, String creditCardName, String creditCardCV, Double creditsUsed) {
        // Create a new Payment object with the amount, card details, and credits used
        Payment payment = new Payment(amount + creditsUsed, creditCardNumber, creditCardName, creditCardCV, new Date(), creditsUsed);

        // Save the payment to the database using the repository's save() method
        return paymentRepository.save(payment);
    }
}
