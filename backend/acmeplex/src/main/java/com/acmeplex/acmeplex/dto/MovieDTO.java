package com.acmeplex.dto;

public class MovieDTO {
    private int movieId;
    private String title;
    private Double duration;
    private String genre;
    private int id;

    public MovieDTO(int movieId, String title, Double duration, String genre, int id) {
        this.movieId = movieId;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.id = id;
    }

    // Getters and setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId() {
        return this.id;
    }
}
