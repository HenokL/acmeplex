package com.acmeplex.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;  
import jakarta.persistence.ManyToOne;  
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Movie")
public class Movie {

    /*
     * Primary key of the Movie table
     * The value of id will be automatically generated by the database when a new Movie entity is created
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId") 
    private int movieId;

    /*
     * title column of the Movie table
     * The title cannot be NULL
     * The length is limited to 200 characters
     */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /*
     * duration column of the Movie table
     * The release date cannot be NULL
     */
    @Column(name = "duration", nullable = false)
    private double duration;

    /*
     * genre column of the Movie table
     * The genre can be NULL
     * The length is limited to 50 characters
     */
    @Column(name = "genre", length = 50) 
    private String genre;

    // All of the movie's showtimes
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Showtime> showtimes;


    /*
     * Default constructor
     * Hibernate requires a no-arg constructor for JPA entities
     */
    public Movie() {}

    /*
     * Parameterized constructor
     */
    public Movie(String title, double duration, String genre) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
    }

    /*
     * Gets the ID value of the movie
     */
    public int getId() {
        return movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    /*
     * Sets the ID value of the movie
     */
    public void setId(int movieId) {
        this.movieId = movieId;
    }

    /*
     * Gets the title of the movie
     */
    public String getTitle() {
        return title;
    }

    /*
     * Sets the title of the movie
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /*
     * Gets the director of the movie
     */
    public double getDuration() {
        return duration;
    }

    /*
     * Sets the director of the movie
     */
    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    /*
     * Sets the genre of the movie
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
     
    /*
     * Override toString for better object representation
     */
    @Override
    public String toString() {
        return "Movie{movieId=" + movieId + ", title='" + title + "'}";
    }
}
