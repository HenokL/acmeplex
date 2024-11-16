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


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Retrieves a list of all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
         // Get all payments from the service
         List<Payment> payments = paymentService.getAllPayments();
         
        // Return the list of payments with HTTP status 200 (OK)
        return ResponseEntity.ok(payments); 
    }

    // Retrieves information about a specific payment
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") int id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);

        if (payment.isPresent()) {
            // Return the movie with OK if found
            return new ResponseEntity<>(payment.get(), HttpStatus.OK);
        } else {
            //  Return 404 not found if the movie does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Process a new payment
    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody Payment paymentRequest) {

        // Create a new payment and save it
        Payment payment = paymentService.createPayment(
                paymentRequest.getAmount(),
                paymentRequest.getCreditCardNumber(),
                paymentRequest.getCreditCardName(),
                paymentRequest.getCreditCardCV()
        );
        // Send sucessful message back
        return ResponseEntity.ok("Payment processed successfully. Payment ID: " + payment.getPaymentId());
    }

    






    
}
