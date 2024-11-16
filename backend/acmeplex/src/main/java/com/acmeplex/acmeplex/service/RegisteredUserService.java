package com.acmeplex.service;
import com.acmeplex.model.RegisteredUser;
import com.acmeplex.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegisteredUserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    // Method to retrieve all registered users
    public List<RegisteredUser> getAllRegisteredUsers() {
        return registeredUserRepository.findAll();
    }

    // Method to retrieve a user by email
    public Optional<RegisteredUser> getUserByEmail(String email) {
        return registeredUserRepository.findByEmail(email);
    }
    
    // Save user to the database
    public RegisteredUser saveUser(RegisteredUser user) {
        return registeredUserRepository.save(user);
    }



}
