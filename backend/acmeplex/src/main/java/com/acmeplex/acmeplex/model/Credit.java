package com.acmeplex.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.Date;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creditID") 
    private int creditID;

    @Column(name = "email") 
    private String email;

    @Column(name = "creditAmount") 
    private double creditAmount;

    @Column(name = "issuedDate") 
    private java.sql.Date issuedDate;

    // Default constructor
    public Credit() {}

    // Constructor with fields
    public Credit(String email, double creditAmount, java.sql.Date issuedDate) {
        this.email = email;
        this.creditAmount = creditAmount;
        this.issuedDate = issuedDate;
    }

    // Getters and Setters
    public int getCreditID() {
        return creditID;
    }

    public void setCreditID(int creditID) {
        this.creditID = creditID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public java.sql.Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(java.sql.Date issuedDate) {
        this.issuedDate = issuedDate;
    }
}
