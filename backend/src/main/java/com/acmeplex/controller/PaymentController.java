package com.acmeplex.controller;
import com.acmeplex.model.Payment;
import com.acmeplex.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")  // Base URL for all payment-related endpoints
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Get all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);  // Return payments with status 200 OK
    }

    // Get payments by payer name
    @GetMapping("/payer/{payerName}")
    public ResponseEntity<List<Payment>> getPaymentsByPayerName(@PathVariable String payerName) {
        List<Payment> payments = paymentService.getPaymentsByPayerName(payerName);
        if (payments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No payments found for the payer name
        }
        return new ResponseEntity<>(payments, HttpStatus.OK);  // Return found payments
    }

    // Get payments by payment method
    @GetMapping("/method/{paymentMethod}")
    public ResponseEntity<List<Payment>> getPaymentsByPaymentMethod(@PathVariable String paymentMethod) {
        List<Payment> payments = paymentService.getPaymentsByPaymentMethod(paymentMethod);
        if (payments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No payments found for the payment method
        }
        return new ResponseEntity<>(payments, HttpStatus.OK);  // Return found payments
    }

    // Get payments greater than or equal to a certain amount
    @GetMapping("/amount/{amount}")
    public ResponseEntity<List<Payment>> getPaymentsByAmountGreaterThanEqual(@PathVariable double amount) {
        List<Payment> payments = paymentService.getPaymentsByAmountGreaterThanEqual(amount);
        if (payments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No payments found for the specified amount
        }
        return new ResponseEntity<>(payments, HttpStatus.OK);  // Return found payments
    }

    // Get a payment by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Payment not found
        }
        return new ResponseEntity<>(payment, HttpStatus.OK);  // Return found payment
    }

    // Create or update a payment
    @PostMapping
    public ResponseEntity<Payment> createOrUpdatePayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.createOrUpdatePayment(payment);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);  // Return the created/updated payment with status 201 CREATED
    }

    // Delete a payment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return status 204 No Content (successful deletion)
    }
}
