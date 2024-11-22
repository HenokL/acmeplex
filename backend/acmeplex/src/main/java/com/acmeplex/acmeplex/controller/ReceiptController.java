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



@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    
    private final ReceiptService receiptService;
    private final EmailService emailService;

    @Autowired
    public ReceiptController(ReceiptService receiptService, EmailService emailService) {
        this.receiptService = receiptService;
        this.emailService = emailService;
    }

    // Get all receipts
    @GetMapping
    public ResponseEntity<List<Receipt>> getAllReceipts() {
        List<Receipt> receipts = receiptService.getAllReceipts();

        // Send all reciepts
        return new ResponseEntity<>(receipts, HttpStatus.OK); 
    }

       // Get receipt by ID
    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable int id) {
        Optional<Receipt> receipt = receiptService.getReceiptById(id);
        if (receipt.isPresent()) {
            return new ResponseEntity<>(receipt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Get receipts by email
    @GetMapping("/email/{email}")
    public ResponseEntity<List<Receipt>> getReceiptsByEmail(@PathVariable String email) {
        List<Receipt> receipts = receiptService.getReceiptsByEmail(email);
        if (receipts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }
    
    // Get receipts by payment ID
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<Receipt>> getReceiptsByPaymentId(@PathVariable int paymentId) {
        List<Receipt> receipts = receiptService.getReceiptsByPaymentId(paymentId);
        if (receipts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if no receipts found
        }
        return new ResponseEntity<>(receipts, HttpStatus.OK); // 200 OK with receipts
    }
}
