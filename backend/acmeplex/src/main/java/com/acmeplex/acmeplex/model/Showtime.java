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
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import java.util.Date;
import java.util.List;



@Entity
@Table(name = "Showtime") // Use lowercase table name
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtimeId") 
    private int showtimeId;

    @ManyToOne
    @JoinColumn(name = "movieId", referencedColumnName = "movieId", nullable = false) 
    private Movie movie;

    // showtimeDate column
    @Column(name = "showtimeDate")
    private java.sql.Date showtimeDate;

    @Column(name = "startTime", nullable = false)
    private String startTime;

    @Column(name = "endTime", nullable = false)
    private String endTime;

    // Maps the seats to the showtime
    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats;

    // Default Constructor
    public Showtime() {
    }

    // Parameterized Constructor
    public Showtime(Movie movie, java.sql.Date showtimeDate, String startTime, String endTime) {
        this.movie = movie;
        this.showtimeDate = showtimeDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setShowtimeDate(java.sql.Date showtimeDate) {
        this.showtimeDate = showtimeDate;
    }

    public java.sql.Date getShowtimeDate() {
        return this.showtimeDate;
    }
    
    // Sets the seats for a showtime
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    // Returns the amount of booked seats for the showtime
    public int getBookedSeats() {
        return seats.size();
    }
}
