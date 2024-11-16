package com.acmeplex.controller;

import com.acmeplex.model.RegisteredUser;
import com.acmeplex.service.RegisteredUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registered-users")  // Base URL for all registered user-related endpoints
public class RegisteredUserController {

    private final RegisteredUserService registeredUserService;

    // Constructor-based dependency injection for RegisteredUserService
    public RegisteredUserController(RegisteredUserService registeredUserService) {
        this.registeredUserService = registeredUserService;
    }

    // Get all registered users
    @GetMapping
    public ResponseEntity<List<RegisteredUser>> getAllRegisteredUsers() {
        List<RegisteredUser> users = registeredUserService.getAllRegisteredUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);  // Return users with status 200 OK
    }

    // Get a registered user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<RegisteredUser> getRegisteredUserByUsername(@PathVariable String username) {
        Optional<RegisteredUser> user = registeredUserService.getRegisteredUserByUsername(username);
        return user.map(u -> new ResponseEntity<>(u, HttpStatus.OK))  // If found, return the user
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));  // If not found, return 404
    }

    // Get a registered user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<RegisteredUser> getRegisteredUserByEmail(@PathVariable String email) {
        Optional<RegisteredUser> user = registeredUserService.getRegisteredUserByEmail(email);
        return user.map(u -> new ResponseEntity<>(u, HttpStatus.OK))  // If found, return the user
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));  // If not found, return 404
    }

    // Get registered users whose membership has expired before a certain date
    @GetMapping("/membership-expiry-before")
    public ResponseEntity<List<RegisteredUser>> getRegisteredUsersByMembershipExpiryDateBefore(
            @RequestParam("expiryDate") Date expiryDate) {  // Automatically bind the date from the request
        List<RegisteredUser> users = registeredUserService.getRegisteredUsersByMembershipExpiryDateBefore(expiryDate);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No users found
        }
        return new ResponseEntity<>(users, HttpStatus.OK);  // Return found users
    }

    // Get registered users with a specific discount rate
    @GetMapping("/discount-rate/{discountRate}")
    public ResponseEntity<List<RegisteredUser>> getRegisteredUsersByDiscountRate(@PathVariable int discountRate) {
        List<RegisteredUser> users = registeredUserService.getRegisteredUsersByDiscountRate(discountRate);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No users found for this discount rate
        }
        return new ResponseEntity<>(users, HttpStatus.OK);  // Return found users
    }

    // Get a registered user by their ID
    @GetMapping("/{id}")
    public ResponseEntity<RegisteredUser> getRegisteredUserById(@PathVariable Long id) {
        RegisteredUser user = registeredUserService.getRegisteredUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // User not found
        }
        return new ResponseEntity<>(user, HttpStatus.OK);  // Return the found user
    }

    // Create or update a registered user
    @PostMapping
    public ResponseEntity<RegisteredUser> createOrUpdateRegisteredUser(@RequestBody RegisteredUser registeredUser) {
        RegisteredUser savedUser = registeredUserService.createOrUpdateRegisteredUser(registeredUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);  // Return the created/updated user
    }

    // Delete a registered user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegisteredUser(@PathVariable Long id) {
        registeredUserService.deleteRegisteredUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return status 204 No Content (successful deletion)
    }
}
