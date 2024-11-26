package com.acmeplex.dto;

/**
 * ShowtimeDTO (Data Transfer Object) is used to transfer showtime-related data,
 * including the showtime ID, start time, end time, and showdate.
 * Author: Riley Koppang
 */
public class ShowtimeDTO {

    // Unique identifier for the showtime (e.g., 101, 102, etc.)
    private int showtimeId;

    // The start time of the showtime (e.g., "10:00 AM", "2:30 PM")
    private String startTime;

    // The end time of the showtime (e.g., "12:00 PM", "4:30 PM")
    private String endTime;

    // The date of the showtime (e.g., "2024-11-25")
    private java.sql.Date showtimeDate;

    /**
     * Constructor to initialize a ShowtimeDTO object with provided showtime details.
     *
     * @param showtimeId The unique ID for the showtime.
     * @param startTime The start time of the showtime.
     * @param endTime The end time of the showtime.
     * @param showtimeDate The date of the showtime.
     */
    public ShowtimeDTO(int showtimeId, String startTime, String endTime, java.sql.Date showtimeDate) {
        this.showtimeId = showtimeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.showtimeDate = showtimeDate;
    }

    // Getters and setters

    /**
     * Gets the showtime ID.
     *
     * @return The unique ID of the showtime.
     */
    public int getShowtimeId() {
        return showtimeId;
    }

    /**
     * Sets the showtime ID.
     *
     * @param showtimeId The showtime ID to set.
     */
    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    /**
     * Gets the start time of the showtime.
     *
     * @return The start time as a String (e.g., "10:00 AM").
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the showtime.
     *
     * @param startTime The start time to set.
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the showtime.
     *
     * @return The end time as a String (e.g., "12:00 PM").
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the showtime.
     *
     * @param endTime The end time to set.
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the date of the showtime.
     *
     * @return The showtime date as a java.sql.Date object.
     */
    public java.sql.Date getShowtimeDate() {
        return showtimeDate;
    }

    /**
     * Sets the date of the showtime.
     *
     * @param showtimeDate The date of the showtime to set.
     */
    public void setShowtimeDate(java.sql.Date showtimeDate) {
        this.showtimeDate = showtimeDate;
    }
}
