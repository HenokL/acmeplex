package com.acmeplex.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalTime;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import java.util.List;

@Entity
@Table(name = "Seat") // Use lowercase table name
public class Seat {

    // Column for Primary key seatId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatId", nullable = false) 
    private int seatId;

    // Column for seat number
    @Column(name = "seatNumber", nullable = false) 
    private int seatNumber;

    // Column for seatRow
    @Column(name = "seatRow", nullable = false)
    private int seatRow;

    // Maps the foreign key to Showtime
    @ManyToOne
    @JoinColumn(name = "showtimeId", nullable = false) 
    private Showtime showtime;

    // Mapping foreign key for Tickets
    @ManyToOne
    @JoinColumn(name = "ticketId", nullable = false)  
    private Ticket ticket; 


    // Default Constructor
    public Seat() {
    }

    // Parameterized Constructor
    public Seat(int seatNumber, int seatRow, Showtime showtime, Ticket ticket) {
        this.seatNumber = seatNumber;
        this.seatRow = seatRow;
        this.showtime = showtime;
        this.ticket = ticket;
    }

    // Getters and Setters
    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    // Setting the ticket
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    // Getting the ticket
    public Ticket getTicket() {
        return this.ticket;
    }
    
}
