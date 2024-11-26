package com.acmeplex.dto;

/**
 * MovieDTO (Data Transfer Object) is used for transferring movie-related data 
 * between layers (e.g., controller to service or service to client).
 * Author: Riley Koppang
 */
public class MovieDTO {

    // Unique identifier for the movie
    private int movieId;
    
    // Title of the movie
    private String title;
    
    // Duration of the movie in minutes
    private Double duration;
    
    // Genre of the movie (e.g., Drama, Comedy, Action)
    private String genre;
    
    // A separate ID for internal system use, possibly to map to other entities
    private int id;

    /**
     * Constructor to initialize a MovieDTO object with provided values.
     *
     * @param movieId The unique identifier for the movie.
     * @param title The title of the movie.
     * @param duration The duration of the movie in minutes.
     * @param genre The genre of the movie.
     * @param id A unique ID for system-level tracking or other purposes.
     */
    public MovieDTO(int movieId, String title, Double duration, String genre, int id) {
        this.movieId = movieId;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.id = id;
    }

    // Getters and setters

    /**
     * Gets the unique identifier for the movie.
     *
     * @return The movie ID.
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Sets the unique identifier for the movie.
     *
     * @param movieId The movie ID to set.
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /**
     * Gets the title of the movie.
     *
     * @return The title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the duration of the movie in minutes.
     *
     * @return The duration of the movie.
     */
    public Double getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the movie in minutes.
     *
     * @param duration The duration to set.
     */
    public void setDuration(Double duration) {
        this.duration = duration;
    }

    /**
     * Gets the genre of the movie.
     *
     * @return The genre of the movie.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the movie.
     *
     * @param genre The genre to set.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets the internal system-level ID of the movie.
     *
     * @return The internal ID.
     */
    public int getId() {
        return this.id;
    }
}
