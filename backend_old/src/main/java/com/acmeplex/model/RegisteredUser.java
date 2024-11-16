package com.acmeplex.model;

import javax.persistence.Entity;
import javax.persistence.Column;
import java.util.Date;

@Entity
public class RegisteredUser extends User {

    /*
     * membershipFee column of the RegisteredUser table
     * The fee that the user pays for membership
     */
    @Column(nullable = false)
    private double membershipFee;

    /*
     * membershipExpiryDate column of the RegisteredUser table
     * The date when the membership expires
     */
    @Column(nullable = false)
    private Date membershipExpiryDate;

    /*
     * discountRate column of the RegisteredUser table
     * The discount rate (in percentage) that the registered user receives
     */
    @Column(nullable = false)
    private int discountRate;

    /*
     * Default constructor
     * Required by JPA
     */
    public RegisteredUser() {
        super(); // Calls the constructor of User
    }

    /*
     * Parameterized constructor
     */
    public RegisteredUser(String username, String password, String email, String firstName, String lastName, 
                          Boolean isRegistered, double membershipFee, Date membershipExpiryDate, int discountRate) {
        super(username, password, email, firstName, lastName, isRegistered);
        this.membershipFee = membershipFee;
        this.membershipExpiryDate = membershipExpiryDate;
        this.discountRate = discountRate;
    }

    /*
     * Method to allow the RegisteredUser to access exclusive seating
     * This is just a placeholder for actual business logic
     */
    public void getExclusiveSeat() {
        System.out.println("Exclusive seat granted to " + getFirstName() + " " + getLastName());
    }

    /*
     * Method to allow the RegisteredUser to pay the annual fee
     * This is just a placeholder for actual business logic
     */
    public void payAnnualFee() {
        System.out.println("Annual fee of " + membershipFee + " paid by " + getFirstName() + " " + getLastName());
    }

    // Getters and Setters for the new attributes

    public double getMembershipFee() {
        return membershipFee;
    }

    public void setMembershipFee(double membershipFee) {
        this.membershipFee = membershipFee;
    }

    public Date getMembershipExpiryDate() {
        return membershipExpiryDate;
    }

    public void setMembershipExpiryDate(Date membershipExpiryDate) {
        this.membershipExpiryDate = membershipExpiryDate;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    /*
     * Override toString() to represent the RegisteredUser object
     */
    @Override
    public String toString() {
        return "RegisteredUser{" +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", membershipFee=" + membershipFee +
                ", membershipExpiryDate=" + membershipExpiryDate +
                ", discountRate=" + discountRate +
                '}';
    }
}
