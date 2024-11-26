package com.acmeplex.controller;

import com.acmeplex.model.Payment;
import com.acmeplex.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * PaymentController handles HTTP requests related to payment data.
 * It includes endpoints to retrieve all payments and details for a specific payment.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // Constructor to inject PaymentService
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Retrieves a list of all payments.
     * 
     * @return A ResponseEntity containing the list of all payments with HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
         // Get all payments from the service
         List<Payment> payments = paymentService.getAllPayments();
         
        // Return the list of payments with HTTP status 200 (OK)
        return ResponseEntity.ok(payments); 
    }

    /**
     * Retrieves information about a specific payment by its ID.
     * 
     * @param id The ID of the payment.
     * @return A ResponseEntity containing the payment data if found, or HTTP status 404 (Not Found) if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") int id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);

        if (payment.isPresent()) {
            // Return the payment with OK status if found
            return new ResponseEntity<>(payment.get(), HttpStatus.OK);
        } else {
            // Return HTTP status 404 (Not Found) if the payment does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
