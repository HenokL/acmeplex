package com.acmeplex.service;
import com.acmeplex.model.Theater;
import com.acmeplex.repository.TheaterRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;

    // Constructor injection of the TheaterRepository
    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    // Get a theater by its name
    public Theater getTheaterByName(String name) {
        return theaterRepository.findByName(name);
    }

    // Get all theaters in a specific location
    public List<Theater> getTheatersByLocation(String location) {
        return theaterRepository.findByLocation(location);
    }

    // Get theaters with a capacity greater than or equal to a certain value
    public List<Theater> getTheatersByMinCapacity(int capacity) {
        return theaterRepository.findByCapacityGreaterThanEqual(capacity);
    }

    // Get theaters that opened after a specific date
    public List<Theater> getTheatersOpenedAfter(Date openingDate) {
        return theaterRepository.findByOpeningDateAfter(openingDate);
    }

    // Get theaters that opened before a specific date
    public List<Theater> getTheatersOpenedBefore(Date openingDate) {
        return theaterRepository.findByOpeningDateBefore(openingDate);
    }

    // Get theaters with a capacity between a certain range
    public List<Theater> getTheatersByCapacityBetween(int minCapacity, int maxCapacity) {
        return theaterRepository.findByCapacityBetween(minCapacity, maxCapacity);
    }

    // Optionally you can add methods to create, update, or delete theaters as needed
    public Theater createTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    public Theater updateTheater(Long theaterId, Theater updatedTheater) {
        Optional<Theater> existingTheater = theaterRepository.findById(theaterId);
        if (existingTheater.isPresent()) {
            Theater theater = existingTheater.get();
            theater.setName(updatedTheater.getName());
            return theaterRepository.save(theater);
        }
        return null;  // Or throw an exception if theater is not found
    }

    public void deleteTheater(Long theaterId) {
        theaterRepository.deleteById(theaterId);
    }
}
