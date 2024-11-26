package com.acmeplex.dto;

/**
 * SeatResponseDTO (Data Transfer Object) is used to transfer seat-related data 
 * in responses, such as when fetching booked seats for a particular showtime.
 * Author: Riley Koppang
 */
public class SeatResponseDTO {

    // The seat number within a specific row
    private int seatNumber;
    
    // The row number of the seat
    private int seatRow;

    /**
     * Constructor to initialize a SeatResponseDTO object with provided seat details.
     *
     * @param seatNumber The number of the seat within a row.
     * @param seatRow The row number of the seat.
     */
    public SeatResponseDTO(int seatNumber, int seatRow) {
        this.seatNumber = seatNumber;
        this.seatRow = seatRow;
    }

    // Getters and setters

    /**
     * Gets the seat number.
     *
     * @return The seat number within the row.
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Sets the seat number.
     *
     * @param seatNumber The seat number to set.
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * Gets the seat row number.
     *
     * @return The row number of the seat.
     */
    public int getSeatRow() {
        return seatRow;
    }

    /**
     * Sets the seat row number.
     *
     * @param seatRow The seat row number to set.
     */
    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }
}
