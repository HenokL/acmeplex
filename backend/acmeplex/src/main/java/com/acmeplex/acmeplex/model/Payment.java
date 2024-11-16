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
@Table(name = "Payment") // Use lowercase table name
public class Payment {

    // Column for Primary key seatId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentId", nullable = false) 
    private int paymentId;

    // Column for amount
    @Column(name = "amount", nullable = false)
    private double amount;

    // Column for credit card number
    @Column(name = "creditCardNumber", nullable = false)
    private long creditCardNumber;

    // Column for credit card name
    @Column(name = "creditCardName", nullable = false)
    private String creditCardName;

     // Column for credit card CV
     @Column(name = "creditCardCV", nullable = false)
     private int creditCardCV;

    // Column for payment date
    @Column(name = "paymentDate", nullable = false)
    private Date paymentDate;

    // Default constructor
    public Payment() {
    }

    // Parameterized constructor
    public Payment(double amount, long creditCardNumber, String creditCardName, int creditCardCV, Date paymentDate) {
        this.amount = amount;
        this.creditCardNumber = creditCardNumber;
        this.creditCardName = creditCardName;
        this.creditCardCV = creditCardCV;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public int getCreditCardCV() {
        return creditCardCV;
    }

    public void setCreditCardCV(int creditCardCV) {
        this.creditCardCV = creditCardCV;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
