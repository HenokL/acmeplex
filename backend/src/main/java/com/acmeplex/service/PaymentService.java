package com.acmeplex.service;

import com.acmeplex.model.Payment;
import com.acmeplex.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // Constructor-based dependency injection for PaymentRepository
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Method to get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Method to get payments by payer name
    public List<Payment> getPaymentsByPayerName(String payerName) {
        return paymentRepository.findByPayerName(payerName);
    }

    // Method to get payments by payment method
    public List<Payment> getPaymentsByPaymentMethod(String paymentMethod) {
        return paymentRepository.findByPaymentMethod(paymentMethod);
    }

    // Method to get payments greater than or equal to a certain amount
    public List<Payment> getPaymentsByAmountGreaterThanEqual(double amount) {
        return paymentRepository.findByAmountGreaterThanEqual(amount);
    }

    // Method to get a payment by ID
    public Payment getPaymentById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.orElse(null); // Return null if the payment doesn't exist
    }

    // Method to create or update a payment
    public Payment createOrUpdatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // Method to delete a payment by ID
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
