package com.acmeplex.dto;

public class SeatResponseDTO {
    private int seatNumber;
    private int seatRow;

    // Constructor
    public SeatResponseDTO(int seatNumber, int seatRow) {
        this.seatNumber = seatNumber;
        this.seatRow = seatRow;
    }

    // Getters and Setters
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
}
