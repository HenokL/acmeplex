package com.acmeplex.service;

import com.acmeplex.model.User;
import com.acmeplex.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection of the UserRepository
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Find a user by their username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Find a user by their email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Find users by their registration status
    public List<User> getUsersByRegistrationStatus(boolean isRegistered) {
        return userRepository.findByIsRegistered(isRegistered);
    }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);  // Save the new user to the database
    }

    // Update an existing user
    public User updateUser(Long userId, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setEmail(updatedUser.getEmail());
            user.setIsRegistered(updatedUser.isRegistered());
            // Add other fields that need updating
            return userRepository.save(user);  // Save the updated user
        }
        return null;  // Return null or throw an exception if user is not found
    }

    // Delete a user
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);  // Delete the user by ID
    }
}
