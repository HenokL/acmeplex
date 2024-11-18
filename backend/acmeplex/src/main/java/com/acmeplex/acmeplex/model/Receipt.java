package com.acmeplex.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalTime;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "Receipt") // Use lowercase table name
public class Receipt {

    // Column for Primary key receiptId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receiptId", nullable = false) 
    private int receiptId;

    // Column for email
    @Column(name = "email", nullable = false) 
    private String email;

    // Foreign key to Payment
    @ManyToOne
    @JoinColumn(name = "paymentId", nullable = false)
    private Payment payment;

    // Foreign key to Ticket
    @ManyToOne
    @JoinColumn(name = "ticketId", nullable = false)
    private Ticket ticket;


    // Default Constructor
    public Receipt() {
    }
    // Parameterized constructor
    public Receipt(String email, Payment payment, Ticket ticket) {
        this.email = email;
        this.payment = payment;
        this.ticket = ticket;
    }

    // Getters and Setters
    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Ticket getTicket() {
        return ticket;
    }

    

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
