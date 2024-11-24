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
;



@RestController
@RequestMapping("/api/credits")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    // Retrieves all of the credits for an email address
    @GetMapping("/{email}")
    public ResponseEntity<Double> getTotalCredits(@PathVariable String email) {
        try {
            // Get total credits for the user from the CreditService
            Double totalCredits = creditService.getTotalCreditByEmail(email);

            // Return total credits as a response
            return ResponseEntity.ok(totalCredits);
        } catch (Exception e) {

            // Handle errors (e.g., user not found, database issues)
            return ResponseEntity.status(500).body(null);
        }
    }


    // Deducts credits from a specified email
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
                // Handle the case where creditsUsed is not a number
                return new ResponseEntity<>("Invalid creditsUsed value", HttpStatus.BAD_REQUEST);
            }

              // Get the total available credit for the user
            double totalCredit = creditService.getTotalCreditByEmail(email);

            // Check if there are enough credits to deduct
            if (creditsUsed > totalCredit) {
                return new ResponseEntity<>("Insufficient credits. Current balance: " + totalCredit, HttpStatus.BAD_REQUEST);
            }

            // Deduct the credits using the CreditService
            creditService.issueCredits(email, -creditsUsed);

            // Return success response with the remaining balance after deduction
            double newTotalCredit = creditService.getTotalCreditByEmail(email);
            return new ResponseEntity<>("Credits deducted successfully. New balance: " + newTotalCredit, HttpStatus.OK);

        } catch (Exception e) {
            // Handle any error
            return new ResponseEntity<>("Error processing the credit deduction: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
