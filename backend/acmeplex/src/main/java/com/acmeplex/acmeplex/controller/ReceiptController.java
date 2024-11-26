package com.acmeplex.controller;

import com.acmeplex.model.Receipt;
import com.acmeplex.service.ReceiptService;
import com.acmeplex.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

/**
 * ReceiptController handles HTTP requests related to receipt data.
 * It includes endpoints for retrieving all receipts, specific receipts by ID, email, or payment ID.
 */
@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;
    private final EmailService emailService;

    // Constructor to inject ReceiptService and EmailService
    @Autowired
    public ReceiptController(ReceiptService receiptService, EmailService emailService) {
        this.receiptService = receiptService;
        this.emailService = emailService;
    }

    /**
     * Retrieves a list of all receipts.
     * 
     * @return A ResponseEntity containing the list of all receipts with HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Receipt>> getAllReceipts() {
        List<Receipt> receipts = receiptService.getAllReceipts();

        // Return all receipts with HTTP status 200 (OK)
        return new ResponseEntity<>(receipts, HttpStatus.OK); 
    }

    /**
     * Retrieves a specific receipt by its ID.
     * 
     * @param id The ID of the receipt.
     * @return A ResponseEntity containing the receipt data if found, or HTTP status 404 (Not Found) if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable int id) {
        Optional<Receipt> receipt = receiptService.getReceiptById(id);
        
        if (receipt.isPresent()) {
            // Return the receipt with HTTP status 200 (OK) if found
            return new ResponseEntity<>(receipt.get(), HttpStatus.OK);
        } else {
            // Return HTTP status 404 (Not Found) if the receipt is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a list of receipts associated with a specific email.
     * 
     * @param email The email of the user.
     * @return A ResponseEntity containing the list of receipts with HTTP status 200 (OK),
     *         or HTTP status 204 (No Content) if no receipts are found.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<List<Receipt>> getReceiptsByEmail(@PathVariable String email) {
        List<Receipt> receipts = receiptService.getReceiptsByEmail(email);

        if (receipts.isEmpty()) {
            // Return HTTP status 204 (No Content) if no receipts found for the email
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // Return the list of receipts with HTTP status 200 (OK)
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }

    /**
     * Retrieves a list of receipts associated with a specific payment ID.
     * 
     * @param paymentId The ID of the payment.
     * @return A ResponseEntity containing the list of receipts with HTTP status 200 (OK),
     *         or HTTP status 204 (No Content) if no receipts are found.
     */
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<Receipt>> getReceiptsByPaymentId(@PathVariable int paymentId) {
        List<Receipt> receipts = receiptService.getReceiptsByPaymentId(paymentId);

        if (receipts.isEmpty()) {
            // Return HTTP status 204 (No Content) if no receipts found for the payment ID
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // Return the list of receipts with HTTP status 200 (OK)
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }
}
