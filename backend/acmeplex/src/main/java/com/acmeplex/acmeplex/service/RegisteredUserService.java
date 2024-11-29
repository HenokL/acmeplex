package com.acmeplex.service;

import com.acmeplex.model.RegisteredUser;
import com.acmeplex.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

/**
 * The RegisteredUserService class provides business logic related to registered users.
 * It contains methods for retrieving, saving, and updating user information in the database.
 * It interacts with the RegisteredUserRepository to perform CRUD operations on registered users.
 * Author: Riley Koppang
 */
@Service
public class RegisteredUserService {

    // Injecting the RegisteredUserRepository to perform database operations
    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    /**
     * Retrieves all registered users from the database.
     * 
     * @return A list of all RegisteredUser objects.
     */
    public List<RegisteredUser> getAllRegisteredUsers() {
        // Retrieves all users using the repository's findAll() method
        return registeredUserRepository.findAll();
    }

    /**
     * Retrieves a registered user based on their email.
     * 
     * @param email The email of the user to retrieve.
     * @return An Optional containing the RegisteredUser if found, or empty if not.
     */
    public Optional<RegisteredUser> getUserByEmail(String email) {
        // Finds a user by their email using the repository's findByEmail() method
        return registeredUserRepository.findByEmail(email);
    }

    /**
     * Saves a registered user to the database.
     * 
     * @param user The RegisteredUser object to save.
     * @return The saved RegisteredUser object.
     */
    public RegisteredUser saveUser(RegisteredUser user) {
        // Saves the user to the database using the repository's save() method
        return registeredUserRepository.save(user);
    }

    /**
     * Checks if a user is already registered based on their email.
     * 
     * @param email The email to check for registration.
     * @return true if the email is already registered, false otherwise.
     */
    public boolean isEmailRegistered(String email) {
        // Checks if a user exists with the given email using the repository's existsByEmail() method
        return registeredUserRepository.existsByEmail(email);
    }

    /**
     * Updates the credit card information for a registered user.
     * 
     * @param email The email of the user whose credit card information is being updated.
     * @param creditCardNumber The new credit card number.
     * @param creditCardCVV The new credit card CVV.
     * @param creditCardExpiryDate The new credit card expiry date.
     */
    public void updateCreditCardInfo(String email, String creditCardNumber, String creditCardCVV,
            String creditCardExpiryDate) {
        // Finds the user by email
        Optional<RegisteredUser> userOptional = registeredUserRepository.findByEmail(email);

        // If the user is found, update their credit card information
        if (userOptional.isPresent()) {
            RegisteredUser user = userOptional.get();
            user.setCreditCardNumber(creditCardNumber);
            user.setCreditCardCVV(creditCardCVV);
            user.setCreditCardExpiryDate(creditCardExpiryDate);
            // Save the updated user entity to the database
            registeredUserRepository.save(user);
        }

        // If the user is not found, do nothing
    }
    
      /**
     * Check if a user's membership has expired.
     * Assumes membership expires 1 year after the purchase date.
     * 
     * @param email The email of the user.
     * @return String indicating whether the membership has expired ("true" or "false").
     */
    public String hasMembershipExpired(String email) {
        Optional<RegisteredUser> userOpt = registeredUserRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            RegisteredUser user = userOpt.get();
            
            // Get the current date and the membership purchase date
            LocalDate currentDate = LocalDate.now();
            LocalDate membershipPurchaseDate = user.getMembershipPurchaseDate().toLocalDate();
            
            // Assuming membership expires 1 year after purchase date
            LocalDate expirationDate = membershipPurchaseDate.plusYears(1);
            
            // Compare the current date to the expiration date
            // Returns "true" if the membership is expired
            if (currentDate.isAfter(expirationDate)) {
                return "true";
            } else {
                return "false";
            }
        } else {
            return "false"; // If user not found, return "false"
        }
    }
}
