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


/**
 * This class represents a Credit entity, which stores information about credits
 * issued to users within the system.
 * Author: Riley Koppang
 */

@Entity
public class Credit {

    // Primary key for the Credit entity, auto-generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creditID") 
    private int creditID;

    // Email associated with the credit 
    @Column(name = "email") 
    private String email;

    // Amount of credit issued to the user
    @Column(name = "creditAmount") 
    private double creditAmount;

    // The date when the credit was issued
    @Column(name = "issuedDate") 
    private java.sql.Date issuedDate;

     // Default constructor for JPA
    public Credit() {}

     /**
     * Constructor to create a new Credit object with specified details.
     * 
     * @param email The email address of the user receiving the credit
     * @param creditAmount The amount of credit issued to the user
     * @param issuedDate The date when the credit was issued
     */
    public Credit(String email, double creditAmount, java.sql.Date issuedDate) {
        this.email = email;
        this.creditAmount = creditAmount;
        this.issuedDate = issuedDate;
    }

    // Getter and Setter for creditID
    public int getCreditID() {
        return creditID;
    }

    public void setCreditID(int creditID) {
        this.creditID = creditID;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for creditAmount
    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    // Getter and Setter for issuedDate
    public java.sql.Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(java.sql.Date issuedDate) {
        this.issuedDate = issuedDate;
    }
}
