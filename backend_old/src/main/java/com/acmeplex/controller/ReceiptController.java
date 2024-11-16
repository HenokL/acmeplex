package com.acmeplex.controller;

import com.acmeplex.model.Receipt;
import com.acmeplex.service.ReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/receipts")  // Base URL for all receipt-related endpoints
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    // Get all receipts
    @GetMapping
    public ResponseEntity<List<Receipt>> getAllReceipts() {
        List<Receipt> receipts = receiptService.getAllReceipts();
        return new ResponseEntity<>(receipts, HttpStatus.OK);  // Return receipts with status 200 OK
    }

    // Get receipts by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Receipt>> getReceiptsByUserId(@PathVariable Long userId) {
        List<Receipt> receipts = receiptService.getReceiptsByUserId(userId);
        if (receipts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No receipts found for the user ID
        }
        return new ResponseEntity<>(receipts, HttpStatus.OK);  // Return found receipts
    }

    // Get receipts by movie ID
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Receipt>> getReceiptsByMovieId(@PathVariable Long movieId) {
        List<Receipt> receipts = receiptService.getReceiptsByMovieId(movieId);
        if (receipts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No receipts found for the movie ID
        }
        return new ResponseEntity<>(receipts, HttpStatus.OK);  // Return found receipts
    }

    // Get receipts by showtime ID
    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<List<Receipt>> getReceiptsByShowtimeId(@PathVariable Long showtimeId) {
        List<Receipt> receipts = receiptService.getReceiptsByShowtimeId(showtimeId);
        if (receipts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No receipts found for the showtime ID
        }
        return new ResponseEntity<>(receipts, HttpStatus.OK);  // Return found receipts
    }

    // Get receipts by calculation date
    @GetMapping("/calculation-date/{calculationDate}")
    public ResponseEntity<List<Receipt>> getReceiptsByCalculationDate(@PathVariable String calculationDate) {
        Date date = parseDate(calculationDate);  // Convert String to Date
        if (date == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Invalid date format
        }
        List<Receipt> receipts = receiptService.getReceiptsByCalculationDate(date);
        if (receipts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No receipts found for the calculation date
        }
        return new ResponseEntity<>(receipts, HttpStatus.OK);  // Return found receipts
    }

    // Get receipts by date range
    @GetMapping("/calculation-date-range")
    public ResponseEntity<List<Receipt>> getReceiptsByCalculationDateBetween(
            @RequestParam String startDate, @RequestParam String endDate) {
        Date start = parseDate(startDate);  // Convert String to Date
        Date end = parseDate(endDate);      // Convert String to Date
        if (start == null || end == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Invalid date format
        }
        List<Receipt> receipts = receiptService.getReceiptsByCalculationDateBetween(start, end);
        if (receipts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No receipts found for the date range
        }
        return new ResponseEntity<>(receipts, HttpStatus.OK);  // Return found receipts
    }

    // Helper method to parse date from string
    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;  // Return null if the date is invalid
        }
    }

    // Get receipts by payment ID
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<Receipt>> getReceiptsByPaymentId(@PathVariable Long paymentId) {
        List<Receipt> receipts = receiptService.getReceiptsByPaymentId(paymentId);
        if (receipts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No receipts found for the payment ID
        }
        return new ResponseEntity<>(receipts, HttpStatus.OK);  // Return found receipts
    }

    // Get a receipt by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable Long id) {
        Receipt receipt = receiptService.getReceiptById(id);
        if (receipt == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Receipt not found
        }
        return new ResponseEntity<>(receipt, HttpStatus.OK);  // Return the found receipt
    }

    // Create or update a receipt
    @PostMapping
    public ResponseEntity<Receipt> createOrUpdateReceipt(@RequestBody Receipt receipt) {
        Receipt savedReceipt = receiptService.createOrUpdateReceipt(receipt);
        return new ResponseEntity<>(savedReceipt, HttpStatus.CREATED);  // Return the created/updated receipt
    }

    // Delete a receipt by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable Long id) {
        receiptService.deleteReceipt(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return status 204 No Content (successful deletion)
    }
}
