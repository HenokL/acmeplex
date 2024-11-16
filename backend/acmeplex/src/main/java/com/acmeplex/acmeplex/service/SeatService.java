package com.acmeplex.service;

import com.acmeplex.model.Showtime;
import com.acmeplex.model.Seat;
import com.acmeplex.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalTime;          
import javax.persistence.OneToMany;  
import javax.persistence.ManyToOne; 
import javax.persistence.JoinColumn; 

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    //  Get showtimes for a specific movie
    public List<Seat> getSeatsByShowtime(Showtime showtime) {
        return seatRepository.findByShowtime(showtime);
    }

    // Retrieves a specific seat by its number and showtime
    public Optional<Seat> getSeatByNumberAndShowtime(int seatNumber, Showtime showtime) {
        return seatRepository.findBySeatNumberAndShowtime(seatNumber, showtime);
    }

    // Book a seat (Save into the database)
    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }
}
