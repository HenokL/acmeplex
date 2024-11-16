package com.acmeplex.service;

import com.acmeplex.model.RegisteredUser;
import com.acmeplex.repository.RegisteredUserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RegisteredUserService {

    private final RegisteredUserRepository registeredUserRepository;

    // Constructor-based dependency injection for RegisteredUserRepository
    public RegisteredUserService(RegisteredUserRepository registeredUserRepository) {
        this.registeredUserRepository = registeredUserRepository;
    }

    // Get all registered users
    public List<RegisteredUser> getAllRegisteredUsers() {
        return registeredUserRepository.findAll();
    }

    // Find a RegisteredUser by username
    public Optional<RegisteredUser> getRegisteredUserByUsername(String username) {
        return registeredUserRepository.findByUsername(username);
    }

    // Find a RegisteredUser by email
    public Optional<RegisteredUser> getRegisteredUserByEmail(String email) {
        return registeredUserRepository.findByEmail(email);
    }

    // Get all registered users whose membership has expired before a certain date
    public List<RegisteredUser> getRegisteredUsersByMembershipExpiryDateBefore(Date date) {
        return registeredUserRepository.findByMembershipExpiryDateBefore(date);
    }

    // Get all registered users with a specific discount rate
    public List<RegisteredUser> getRegisteredUsersByDiscountRate(int discountRate) {
        return registeredUserRepository.findByDiscountRate(discountRate);
    }

    // Get a specific registered user by their ID
    public RegisteredUser getRegisteredUserById(Long id) {
        Optional<RegisteredUser> registeredUser = registeredUserRepository.findById(id);
        return registeredUser.orElse(null); // Return null if the user doesn't exist
    }

    // Create or update a registered user
    public RegisteredUser createOrUpdateRegisteredUser(RegisteredUser registeredUser) {
        return registeredUserRepository.save(registeredUser);
    }

    // Delete a registered user by their ID
    public void deleteRegisteredUser(Long id) {
        registeredUserRepository.deleteById(id);
    }
}
