package com.acmeplex.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acmeplex.model.Credit;
import com.acmeplex.repository.CreditRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class CreditService {

    @Autowired
    private CreditRepository creditRepository;

    public Double getTotalCreditByEmail(String email) {
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
    

     // Method to add credits to a user's account
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
