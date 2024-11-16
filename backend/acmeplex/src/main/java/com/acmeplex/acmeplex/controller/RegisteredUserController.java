package com.acmeplex.controller;
import com.acmeplex.model.RegisteredUser;
import com.acmeplex.service.RegisteredUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
     public ResponseEntity<RegisteredUser> createUser(@RequestBody RegisteredUser user) {
         RegisteredUser savedUser = registeredUserService.saveUser(user);
         
         // Return the saved user with HTTP status 201 (Created)
         return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
     }


}
