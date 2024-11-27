package com.acmeplex.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import com.acmeplex.model.RegisteredUser;
import com.acmeplex.service.RegisteredUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.sql.Date; 
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * RegisteredUserController handles HTTP requests related to registered user data.
 * It includes endpoints for retrieving all registered users, a specific user by email,
 * creating a new user, and logging in a user.
 * Author: Riley Koppang
 */
@RestController
@RequestMapping("/api")
public class RegisteredUserController {

    private final RegisteredUserService registeredUserService;


    // Constructor to inject the RegisteredUserService
    public RegisteredUserController(RegisteredUserService registeredUserService) {
        this.registeredUserService = registeredUserService;
    }

    /**
     * Retrieves a list of all registered users.
     * 
     * @return A ResponseEntity containing the list of all users with HTTP status 200 (OK).
     */
    @GetMapping("/users")
    public ResponseEntity<List<RegisteredUser>> getAllRegisteredUsers() {
        // Get all registered users from the service
        List<RegisteredUser> users = registeredUserService.getAllRegisteredUsers();
        
        // Return the list of users with HTTP status 200 (OK)
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a specific registered user by their email.
     * 
     * @param email The email of the user.
     * @return A ResponseEntity containing the user data if found, or HTTP status 404 (Not Found) if not found.
     */
    @GetMapping("/users/{email}")
    public ResponseEntity<RegisteredUser> getUserByEmail(@PathVariable("email") String email) {
        Optional<RegisteredUser> user = registeredUserService.getUserByEmail(email);

        if (user.isPresent()) {
            // Return the user with HTTP status 200 (OK) if found
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            // Return HTTP status 404 (Not Found) if the user does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates the membershipPurchaseDate to the current date for the given user.
     * 
     * @param email The email of the user whose membershipPurchaseDate needs to be updated.
     * @return A ResponseEntity indicating whether the update was successful or not.
     */
    @PatchMapping("/users/{email}/membership")
    public ResponseEntity<?> updateMembershipPurchaseDate(@PathVariable("email") String email) {
        Optional<RegisteredUser> userOpt = registeredUserService.getUserByEmail(email);

        if (userOpt.isPresent()) {
            RegisteredUser user = userOpt.get();

            // Update the membershipPurchaseDate to the current date
            LocalDate currentDate = LocalDate.now();
            Date sqlDate = Date.valueOf(currentDate);

            // Assuming you have a method in your service to update the user
            user.setMembershipPurchaseDate(sqlDate);
            registeredUserService.saveUser(user);

            return new ResponseEntity<>("Membership purchase date updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }




    /**
     * Creates a new registered user.
     * 
     * @param user The user object to be saved.
     * @return A ResponseEntity containing the saved user with HTTP status 201 (Created),
     *         or a 400 (Bad Request) if the email already exists.
     */
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> userDetails) {
        
        try {

            // Parse the JSON request
            String email = userDetails.get("email");
            String name = userDetails.get("name");
            String password = userDetails.get("password");

             // Set the current date as the membership purchase date
            LocalDate currentDate = LocalDate.now();
            Date sqlDate = Date.valueOf(currentDate);

            // Save the registeredUser through their constructor
            RegisteredUser regUser = new RegisteredUser(name, email, password, sqlDate);


            // Attempt to save the user
            RegisteredUser savedUser = registeredUserService.saveUser(regUser);

            // Return the saved user with HTTP status 201 (Created)
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (Exception ex) {
            // Handle all exceptions, such as duplicate email, and return a simple error message
            return new ResponseEntity<>("Duplicate Email Try Again", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Logs in a registered user.
     * 
     * @param loginDetails A map containing email and password for login.
     * @return A ResponseEntity containing a success or error message along with user data
     *         or an error message depending on the login success or failure.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> loginDetails) {
        String email = loginDetails.get("email");
        String password = loginDetails.get("password");

        // Fetch user by email
        Optional<RegisteredUser> userOpt = registeredUserService.getUserByEmail(email);

        if (userOpt.isPresent()) {
            RegisteredUser user = userOpt.get();

            // Check if the membership has expired
            String expired = registeredUserService.hasMembershipExpired(email);

            // Check if the password matches (assuming passwords are stored in plain text, for demo purposes)
            if (user.getPassword().equals(password)) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("userId", String.valueOf(user.getUserId()));
                response.put("creditCardNumber", user.getCreditCardNumber());
                response.put("creditCardCVV", user.getCreditCardCVV());
                response.put("creditCardExpiryDate", user.getCreditCardExpiryDate());
                response.put("membershipExpired", expired);

                // Return login success message with user details
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // Return HTTP status 400 (Bad Request) if the password is incorrect
                Map<String, String> response = new HashMap<>();
                response.put("message", "Invalid password");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            // Return HTTP status 404 (Not Found) if the user does not exist
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
