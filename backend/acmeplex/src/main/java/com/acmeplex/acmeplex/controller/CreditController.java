package com.acmeplex.controller;

import com.acmeplex.model.Credit;
import com.acmeplex.service.CreditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.time.LocalDate;

/**
 * CreditController handles HTTP requests related to user credits.
 * It includes endpoints to retrieve total credits for a user and to deduct credits.
 * Author: Riley Koppang
 */
@RestController
@RequestMapping("/api/credits")
public class CreditController {

    private final CreditService creditService;

    // Constructor to inject CreditService
    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    /**
     * Retrieves the total credits available for a user based on their email address.
     * 
     * @param email The email of the user for whom the credits are being requested.
     * @return A ResponseEntity containing the total credits for the user or an error response.
     */
    @GetMapping("/{email}")
    public ResponseEntity<Double> getTotalCredits(@PathVariable String email) {
        try {
            // Get total credits for the user from the CreditService
            Double totalCredits = creditService.getTotalCreditByEmail(email);

            // Return total credits as a response with HTTP status 200 (OK)
            return ResponseEntity.ok(totalCredits);
        } catch (Exception e) {
            // Return an error response with HTTP status 500 (Internal Server Error) in case of failure
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Deducts a specified amount of credits from a user's account.
     * 
     * @param request A Map containing the email and the amount of credits to deduct (creditsUsed).
     * @return A ResponseEntity indicating success or failure, including the updated credit balance.
     */
    @PostMapping("/deductCredits")
    public ResponseEntity<?> deductCredits(@RequestBody Map<String, Object> request) {
        try {
            // Extract email and creditsUsed from the request body
            String email = (String) request.get("email");
            Object creditsUsedObj = request.get("creditsUsed");

            // Convert creditsUsed to double
            double creditsUsed = 0.0;
            if (creditsUsedObj instanceof Number) {
                creditsUsed = ((Number) creditsUsedObj).doubleValue();
            } else {
                // Return error if creditsUsed is not a valid number
                return new ResponseEntity<>("Invalid creditsUsed value", HttpStatus.BAD_REQUEST);
            }

            // Get the total available credit for the user
            double totalCredit = creditService.getTotalCreditByEmail(email);

            // Check if there are enough credits to deduct
            if (creditsUsed > totalCredit) {
                // Return error if insufficient credits
                return new ResponseEntity<>("Insufficient credits. Current balance: " + totalCredit, HttpStatus.BAD_REQUEST);
            }

            // Deduct the credits using the CreditService (negative value to subtract)
            creditService.issueCredits(email, -creditsUsed);

            // Get the new total credit after deduction
            double newTotalCredit = creditService.getTotalCreditByEmail(email);

            // Return success response with the new balance after deduction
            return new ResponseEntity<>("Credits deducted successfully. New balance: " + newTotalCredit, HttpStatus.OK);

        } catch (Exception e) {
            // Return an error response with HTTP status 500 (Internal Server Error) in case of failure
            return new ResponseEntity<>("Error processing the credit deduction: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
