package com.acmeplex.service;
import com.acmeplex.model.Receipt;
import com.acmeplex.model.Payment;
import com.acmeplex.model.Seat;

import com.acmeplex.model.Ticket;
import com.acmeplex.repository.ReceiptRepository;
import com.acmeplex.repository.PaymentRepository;
import com.acmeplex.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalTime;          
import javax.persistence.OneToMany;  
import javax.persistence.ManyToOne; 
import javax.persistence.JoinColumn; 

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EmailService emailService;

    // Get all receipts
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }
    
    // Get reciept by ticket id
    public Optional<Receipt> getReceiptById(int id) {
        return receiptRepository.findById(id);
    }

    // Get receipt by email
    public List<Receipt> getReceiptsByEmail(String email) {
        return receiptRepository.findByEmail(email);
    }

    // Get reciepts by paymentId
    public List<Receipt> getReceiptsByPaymentId(int paymentId) {
        return receiptRepository.findByPayment_PaymentId(paymentId);
    }

    // get reciepts based on ticket ID
    public List<Receipt> getReceiptsByTicketId(int ticketId) {
        return receiptRepository.findByTicket_TicketId(ticketId);
    }


    public void sendReceiptEmail(Receipt receipt) {
        StringBuilder emailBody = new StringBuilder(); 
    
        emailBody.append("Dear Customer,\n\n")
                 .append("Thank you for your purchase! Here are the details of your receipt:\n\n")
                 .append("Receipt ID: ").append(receipt.getReceiptId()).append("\n")
                 .append("Payment ID: ").append(receipt.getPayment().getPaymentId()).append("\n")
                 .append("Ticket ID: ").append(receipt.getTicket().getTicketId()).append("\n")
                 .append("Movie: ").append(receipt.getTicket().getMovie().getTitle()).append("\n");
    
        // Loop through the seats associated with the ticket
        emailBody.append("Seats: \n");
        for (Seat seat : receipt.getTicket().getSeats()) {
            emailBody.append("Seat Number: ").append(seat.getSeatNumber())
                     .append(" (Row: ").append(seat.getSeatRow()).append(")\n");
        }
    
        emailBody.append("Price: ").append(receipt.getTicket().getPrice()).append("\n\n")
                 .append("Regards,\n")
                 .append("Acmeplex Support");
    
        // Send the email
        emailService.sendEmail(receipt.getEmail(), "Your Receipt", emailBody.toString());
    }
    

    // Create reciept
    public Receipt createReceipt(String email, int paymentId, int ticketId) {
        // Fetch the Payment and Ticket based on their IDs
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Create a new Receipt with the fetched Payment and Ticket
        Receipt receipt = new Receipt();
        receipt.setEmail(email);
        receipt.setPayment(payment);
        receipt.setTicket(ticket);

        // Save the new Receipt to the database
        Receipt savedReceipt = receiptRepository.save(receipt);

        // Sending out the Receipt Via email
        sendReceiptEmail(savedReceipt);

        return savedReceipt;
    }
    
}
