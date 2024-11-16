package com.acmeplex.controller;
import com.acmeplex.model.Theater;
import com.acmeplex.service.TheaterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/theaters")  // Base URL for all theater-related endpoints
public class TheaterController {

    private final TheaterService theaterService;

    // Constructor-based dependency injection for TheaterService
    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    // Get a theater by its name
    @GetMapping("/name/{name}")
    public ResponseEntity<Theater> getTheaterByName(@PathVariable String name) {
        Theater theater = theaterService.getTheaterByName(name);
        if (theater == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Theater not found
        }
        return new ResponseEntity<>(theater, HttpStatus.OK);  // Return the found theater
    }

    // Get all theaters in a specific location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Theater>> getTheatersByLocation(@PathVariable String location) {
        List<Theater> theaters = theaterService.getTheatersByLocation(location);
        if (theaters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No theaters found for this location
        }
        return new ResponseEntity<>(theaters, HttpStatus.OK);  // Return the theaters in the location
    }

    // Get theaters with a capacity greater than or equal to a certain value
    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<List<Theater>> getTheatersByMinCapacity(@PathVariable int capacity) {
        List<Theater> theaters = theaterService.getTheatersByMinCapacity(capacity);
        if (theaters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No theaters found with sufficient capacity
        }
        return new ResponseEntity<>(theaters, HttpStatus.OK);  // Return the theaters with sufficient capacity
    }

    // Get theaters with a capacity between a certain range
    @GetMapping("/capacityBetween/{minCapacity}/{maxCapacity}")
    public ResponseEntity<List<Theater>> getTheatersByCapacityBetween(@PathVariable int minCapacity,
                                                                     @PathVariable int maxCapacity) {
        List<Theater> theaters = theaterService.getTheatersByCapacityBetween(minCapacity, maxCapacity);
        if (theaters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // No theaters found within the given capacity range
        }
        return new ResponseEntity<>(theaters, HttpStatus.OK);  // Return the theaters within the capacity range
    }

    // Create a new theater
    @PostMapping
    public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
        Theater savedTheater = theaterService.createTheater(theater);
        return new ResponseEntity<>(savedTheater, HttpStatus.CREATED);  // Return the created theater
    }

    // Update an existing theater
    @PutMapping("/{id}")
    public ResponseEntity<Theater> updateTheater(@PathVariable Long id, @RequestBody Theater updatedTheater) {
        Theater theater = theaterService.updateTheater(id, updatedTheater);
        if (theater == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Theater not found
        }
        return new ResponseEntity<>(theater, HttpStatus.OK);  // Return the updated theater
    }

    // Delete a theater by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(@PathVariable Long id) {
        theaterService.deleteTheater(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return status 204 No Content (successful deletion)
    }
}
