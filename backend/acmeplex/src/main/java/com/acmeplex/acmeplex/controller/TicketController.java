package com.acmeplex.controller;

import com.acmeplex.model.Ticket;
import com.acmeplex.model.Showtime;
import com.acmeplex.model.Payment;
import com.acmeplex.model.Seat;
import com.acmeplex.model.Receipt;
import com.acmeplex.service.TicketService;
import com.acmeplex.service.ShowtimeService;
import com.acmeplex.service.PaymentService;
import com.acmeplex.service.SeatService;
import com.acmeplex.service.ReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ShowtimeService showtimeService;
    private final PaymentService paymentService;
    private final SeatService seatService;
    private final ReceiptService receiptService;




    // Constructor-based dependency injection
    public TicketController(TicketService ticketService, ShowtimeService showtimeService, PaymentService paymentService, SeatService seatService, ReceiptService receiptService) {
        this.ticketService = ticketService;
        this.showtimeService = showtimeService;
        this.paymentService = paymentService;
        this.seatService = seatService;
        this.receiptService = receiptService;
    }

    // Get ticket by its ID
    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        if (ticket != null) {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all tickets
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    // Cancelling a ticket for an ordinary user
    @PutMapping("/cancel/{ticketId}")
    public ResponseEntity<String> cancelTicket(@PathVariable int ticketId, @RequestBody Map<String, String> request) {

        // Fetching the ticket
        Ticket ticket = ticketService.getTicketById(ticketId);

        // Check if the ticket is found
        if (ticket == null) {
            return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
        }

        // Check if the ticket is already cancelled
        if (ticket.getStatus().equals("Cancelled")) {
            return new ResponseEntity<>("Ticket is already cancelled", HttpStatus.BAD_REQUEST);
        }

        // Getting the showtime
        Showtime showtime = ticket.getShowtime();

        // Getting the Date of the showtime
        java.sql.Date sqlShowtimeDate = showtime.getShowtimeDate();

        // Convert java.sql.Date to java.time.LocalDate
        LocalDate showtimeDate = sqlShowtimeDate.toLocalDate();

        // Getting the start time of the showtime
        String startTimeString = showtime.getStartTime();
        LocalTime showtimeStartTime = LocalTime.parse(startTimeString);

        // Combine the date and time into a full LocalDateTime
        LocalDateTime showtimeStart = LocalDateTime.of(showtimeDate, showtimeStartTime);

        // Getting the current time
        LocalDateTime now = LocalDateTime.now();

        // Calculating the time difference between now and the showtime
        Duration duration = Duration.between(now, showtimeStart);

        // Check if the difference is less or equal to 72 hours
        if (duration.toHours() <= 72) {
            return new ResponseEntity<>("Cannot cancel ticket within 72 hours of the showtime", HttpStatus.BAD_REQUEST);
        }

        // Get email from request body
        String email = request.get("email");

        // Check if the email is registered

        // Proceed to cancel the ticket if it is within the cancelable time frame
        ticketService.cancelTicket(ticketId, email);

        return new ResponseEntity<>("Ticket deleted successfully", HttpStatus.OK);
    }

    /*
     * Creates a new Ticket and payment
     * The new Ticket and new Payment together form the Reciept
     */
    @PostMapping("/book")
    public ResponseEntity<String> bookTicket(@RequestBody Map<String, Object> request) {
        try {
            // Extract showtimeId, seat info, and payment details from the request
            int showtimeId = (int) request.get("showtimeId");
            int seatNumber = (int) request.get("seatNumber");
            int seatRow = (int) request.get("seatRow");
            int seatId = (int) request.get("seatId");
            double price = (double) request.get("price");
            long creditCardNumber = (long) request.get("creditCardNumber");
            String creditCardName = (String) request.get("creditCardName");
            int creditCardCV = (int) request.get("creditCardCV");
            String email = (String) request.get("email");

            // Retreive showtime and movieID
            Showtime showtime = showtimeService.getShowtimeById(showtimeId)
                    .orElseThrow(() -> new RuntimeException("Showtime not found for ID " + showtimeId));
            int movieId = showtime.getMovie().getMovieId();  // Get movieId from Showtime
            

        
            // Create the payment
            Payment payment = paymentService.createPayment(price, creditCardNumber, creditCardName, creditCardCV);

            // Book the ticket
            Ticket bookedTicket = ticketService.saveTicket(movieId, seatId, showtimeId);
                
            // Save the receipt to the database
            Receipt receipt = receiptService.createReceipt(email, payment.getPaymentId(), bookedTicket.getTicketId());

            // Step 4: Send receipt email (using receiptService)
            receiptService.sendReceiptEmail(receipt);

            // Return success message
            return new ResponseEntity<>("Ticket booked and receipt created successfully", HttpStatus.CREATED);


            } catch (Exception e) {
                // Handle errors (e.g., payment failure, booking failure)
                return new ResponseEntity<>("Error processing the booking", HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
}
