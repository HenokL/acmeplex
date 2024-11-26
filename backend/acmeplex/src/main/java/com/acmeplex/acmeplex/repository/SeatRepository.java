package com.acmeplex.repository;

import com.acmeplex.model.Showtime;
import com.acmeplex.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * The SeatRepository interface provides data access methods for interacting with the Seat entity.
 * It extends JpaRepository, enabling standard CRUD operations as well as custom query methods to interact with the Seat table.
 * Author: Riley Koppang
 */
@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    /**
     * Finds all seats for a specific showtime.
     * This method retrieves all the seats associated with a given showtime.
     * 
     * @param showtime The showtime for which to find the associated seats.
     * @return A list of Seat entities for the specified showtime.
     */
    List<Seat> findByShowtime(Showtime showtime);

    /**
     * Finds a specific seat by its seat number and showtime.
     * This method searches for a seat based on its seat number and associated showtime.
     * 
     * @param seatNumber The seat number of the seat to search for.
     * @param showtime The showtime associated with the seat to search for.
     * @return An Optional containing the Seat entity if found, or empty if not found.
     */
    Optional<Seat> findBySeatNumberAndShowtime(int seatNumber, Showtime showtime);

    /**
     * Checks if a seat exists by its seat ID.
     * This method checks whether a seat with the specified seat ID exists in the database.
     * 
     * @param seatId The ID of the seat to check.
     * @return true if a seat with the given seat ID exists, false otherwise.
     */
    boolean existsBySeatId(int seatId);

    /**
     * Checks if a seat exists for a specific showtime with a given seat number and row.
     * This method checks if a seat with the specified seat number, seat row, and showtime already exists in the database.
     * 
     * @param seatNumber The seat number to check.
     * @param seatRow The row number of the seat to check.
     * @param showtimeId The ID of the showtime to check against.
     * @return true if the seat exists for the specified showtime, seat number, and seat row, false otherwise.
     */
    boolean existsBySeatNumberAndSeatRowAndShowtime_ShowtimeId(int seatNumber, int seatRow, int showtimeId);
}
