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

/**
 * The ReceiptService class contains business logic related to receipts.
 * It provides methods to retrieve, create, and send receipt details.
 * It interacts with the ReceiptRepository, PaymentRepository, TicketRepository, and EmailService.
 * Author: Riley Koppang
 */
@Service
public class ReceiptService {

    // Injecting the necessary repositories and email service
    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Retrieves all receipts from the database.
     * 
     * @return A list of all Receipt objects.
     */
    public List<Receipt> getAllReceipts() {
        // Retrieves all receipts using the repository's findAll() method
        return receiptRepository.findAll();
    }

    /**
     * Retrieves a specific receipt based on its ID.
     * 
     * @param id The ID of the receipt to retrieve.
     * @return An Optional containing the Receipt if found, or empty if not.
     */
    public Optional<Receipt> getReceiptById(int id) {
        // Finds a receipt by its ID using the repository's findById() method
        return receiptRepository.findById(id);
    }

    /**
     * Retrieves receipts based on the user's email address.
     * 
     * @param email The email of the user to retrieve receipts for.
     * @return A list of receipts associated with the given email.
     */
    public List<Receipt> getReceiptsByEmail(String email) {
        // Finds receipts by the user's email using the repository's findByEmail() method
        return receiptRepository.findByEmail(email);
    }

    /**
     * Retrieves receipts based on the payment ID.
     * 
     * @param paymentId The ID of the payment to find receipts for.
     * @return A list of receipts associated with the given payment ID.
     */
    public List<Receipt> getReceiptsByPaymentId(int paymentId) {
        // Finds receipts by payment ID using the repository's findByPayment_PaymentId() method
        return receiptRepository.findByPayment_PaymentId(paymentId);
    }

    /**
     * Retrieves receipts based on the ticket ID.
     * 
     * @param ticketId The ID of the ticket to find receipts for.
     * @return A list of receipts associated with the given ticket ID.
     */
    public List<Receipt> getReceiptsByTicketId(int ticketId) {
        // Finds receipts by ticket ID using the repository's findByTicket_TicketId() method
        return receiptRepository.findByTicket_TicketId(ticketId);
    }

    /**
     * Sends an email containing receipt details to the customer.
     * 
     * @param receipt The Receipt object containing the information to be sent.
     */
    public void sendReceiptEmail(Receipt receipt) {
        // Build the email body content
        StringBuilder emailBody = new StringBuilder();

        emailBody.append("Dear Customer,\n\n")
                 .append("Thank you for your purchase! Here are the details of your receipt:\n\n")
                 .append("Receipt ID: ").append(receipt.getReceiptId()).append("\n")
                 .append("Payment ID: ").append(receipt.getPayment().getPaymentId()).append("\n")
                 .append("Ticket ID: ").append(receipt.getTicket().getTicketId()).append("\n")
                 .append("Movie: ").append(receipt.getTicket().getMovie().getTitle()).append("\n");

        // Loop through the seats associated with the ticket and append them to the email
        emailBody.append("Seats: \n");
        for (Seat seat : receipt.getTicket().getSeats()) {
            emailBody.append("Seat Number: ").append(seat.getSeatNumber())
                     .append(" (Row: ").append(seat.getSeatRow()).append(")\n");
        }

        // Append price and closing
        emailBody.append("Price: ").append(receipt.getTicket().getPrice()).append("\n\n")
                 .append("Regards,\n")
                 .append("Acmeplex Support");

        // Send the email using the EmailService
        emailService.sendEmail(receipt.getEmail(), "Your Receipt", emailBody.toString());
    }

    /**
     * Creates a new receipt and sends it via email.
     * 
     * @param email The email address of the customer.
     * @param paymentId The ID of the payment for the receipt.
     * @param ticketId The ID of the ticket for the receipt.
     * @return The saved Receipt object.
     */
    public Receipt createReceipt(String email, int paymentId, int ticketId) {
        // Fetch the Payment and Ticket based on their IDs
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Create a new Receipt and associate it with the Payment and Ticket
        Receipt receipt = new Receipt();
        receipt.setEmail(email);
        receipt.setPayment(payment);
        receipt.setTicket(ticket);

        // Save the new Receipt to the database
        Receipt savedReceipt = receiptRepository.save(receipt);

        // Send the receipt via email to the customer
        sendReceiptEmail(savedReceipt);

        // Return the saved Receipt
        return savedReceipt;
    }
}
