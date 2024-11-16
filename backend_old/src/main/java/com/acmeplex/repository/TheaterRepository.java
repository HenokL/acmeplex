package com.acmeplex.repository;
import com.acmeplex.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    // Find a theater by its name
    Theater findByName(String name);

    // Find all theaters by location
    List<Theater> findByLocation(String location);

    // Find theaters by capacity greater than or equal to a certain number
    List<Theater> findByCapacityGreaterThanEqual(int capacity);

    // Find all theaters that opened after a specific date
    List<Theater> findByOpeningDateAfter(Date openingDate);

    // Find all theaters that opened before a specific date
    List<Theater> findByOpeningDateBefore(Date openingDate);

    // Find all theaters with a capacity between two values
    List<Theater> findByCapacityBetween(int minCapacity, int maxCapacity);
}
