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
import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/api")
public class RegisteredUserController {

    
    private final RegisteredUserService registeredUserService;

    public RegisteredUserController(RegisteredUserService registeredUserService) {
        this.registeredUserService = registeredUserService;
    }

    // Retrieves a list of all movies
    @GetMapping("/users")
    public ResponseEntity<List<RegisteredUser>> getAllRegisteredUsers() {
         // Get all movies from the service
         List<RegisteredUser> users = registeredUserService.getAllRegisteredUsers();
         
        // Return the list of movies with HTTP status 200 (OK)
        return ResponseEntity.ok(users); 
    }

    // Retrieves user by specific email
    @GetMapping("/users/{email}")
    public ResponseEntity<RegisteredUser> getUserByEmail(@PathVariable("email") String email) {
        Optional<RegisteredUser> user = registeredUserService.getUserByEmail(email);

        if (user.isPresent()) {
            // Return the movie with OK if found
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            //  Return 404 not found if the movie does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     // Store a new registered user
     @PostMapping("/users")
     public ResponseEntity<?> createUser(@RequestBody RegisteredUser user) {
         try {
             // Attempt to save the user
             RegisteredUser savedUser = registeredUserService.saveUser(user);
     
             // Return the saved user with HTTP status 201 (Created)
             return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
     
         } catch (Exception ex) {
             // Handle all exceptions and return a simple error message
             return new ResponseEntity<>("Duplicate Email Try Again", HttpStatus.BAD_REQUEST);
         }
     }
     
     //  Login in a user
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> loginDetails) {
        String email = loginDetails.get("email");
        String password = loginDetails.get("password");

        // Fetch user by email
        Optional<RegisteredUser> userOpt = registeredUserService.getUserByEmail(email);

        if (userOpt.isPresent()) {
            RegisteredUser user = userOpt.get();

            // Check if the password matches (assuming passwords are stored in plain text, for demo purposes)
            if (user.getPassword().equals(password)) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("userId", String.valueOf(user.getUserId())); // Assuming the user has an ID field
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Invalid password");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    
}
