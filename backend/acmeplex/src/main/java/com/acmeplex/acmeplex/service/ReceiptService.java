package com.acmeplex.service;
import com.acmeplex.model.Receipt;
import com.acmeplex.model.Payment;
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
        return receiptRepository.save(receipt);
    }
    
}
