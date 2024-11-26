package com.acmeplex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acmeplex.model.Credit;
import com.acmeplex.repository.CreditRepository;
import java.time.LocalDate;
import java.util.List;

/**
 * The CreditService class provides business logic for managing user credits.
 * It handles operations like calculating the total credit for a user within the last year
 * and issuing new credits to a user's account.
 * Author: Riley Koppang
 */
@Service
public class CreditService {

    // Injecting CreditRepository to interact with the database for Credit-related operations
    @Autowired
    private CreditRepository creditRepository;

    /**
     * Calculates the total credit amount for a user within the last year based on their email.
     * This method retrieves all credits for the given email and filters them based on the issued date.
     * Only credits issued in the last year are included in the total.
     * 
     * @param email The email address of the user whose credits are being calculated.
     * @return The total credit amount for the user within the last year.
     */
    public Double getTotalCreditByEmail(String email) {
        // Retrieve all credits associated with the given email
        List<Credit> credits = creditRepository.findAllByEmail(email);
    
        // Sum the creditAmount for credits within the last year
        return credits.stream()
                .filter(credit -> {
                    // Ensure proper conversion from java.sql.Date to LocalDate
                    if (credit.getIssuedDate() != null) {
                        // Convert java.sql.Date to LocalDate and compare it with LocalDate.now().minusYears(1)
                        LocalDate issuedDate = credit.getIssuedDate().toLocalDate();
                        return issuedDate.isAfter(LocalDate.now().minusYears(1));
                    }
                    return false;
                })
                .mapToDouble(Credit::getCreditAmount)
                .sum();
    }
    
    /**
     * Issues new credits to a user's account.
     * This method creates a new credit entry for the given user, sets the credit amount and the current date,
     * and saves it to the database.
     * 
     * @param email The email address of the user to whom credits are being issued.
     * @param amount The amount of credit to be issued.
     */
    public void issueCredits(String email, double amount) {
        // Create a new Credit object with the user's email, the amount, and today's date
        Credit credit = new Credit();
        credit.setEmail(email);
        credit.setCreditAmount(amount);
        credit.setIssuedDate(java.sql.Date.valueOf(LocalDate.now()));  

        // Save the credit to the database
        creditRepository.save(credit);
    }

}
