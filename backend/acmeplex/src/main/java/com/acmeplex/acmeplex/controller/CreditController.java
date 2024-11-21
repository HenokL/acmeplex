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
}
