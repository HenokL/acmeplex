package com.acmeplex.dto;

public class ShowtimeDTO {
    private int showtimeId;
    private String startTime;
    private String endTime;
    private java.sql.Date showtimeDate;

    // Constructor
    public ShowtimeDTO(int showtimeId, String startTime, String endTime, java.sql.Date showtimeDate) {
        this.showtimeId = showtimeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.showtimeDate = showtimeDate;
    }

     // Getters and Setters
     public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
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

    public java.sql.Date getShowtimeDate() {
        return showtimeDate;
    }

    public void setShowtimeDate(java.sql.Date showtimeDate) {
        this.showtimeDate = showtimeDate;
    }
    
}
