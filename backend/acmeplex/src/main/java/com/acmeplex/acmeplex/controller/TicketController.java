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
import java.util.ArrayList;
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

    // Cancelling a ticket
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

        // Getting the current date
        LocalDate now = LocalDate.now();

        // Calculate the number of days between now and the showtime
        long daysBetween = Duration.between(now.atStartOfDay(), showtimeDate.atStartOfDay()).toDays();

        // Check if the difference is less than or equal to 3 days
        if (daysBetween <= 3) {
            return new ResponseEntity<>("Cannot cancel ticket within 3 days of the showtime", HttpStatus.BAD_REQUEST);
        }
        // Get email from request body
        String email = request.get("email");

         // Validate email with the one stored in the ticket
        if (!email.equals(ticket.getEmail())) {
            return new ResponseEntity<>("Email does not match the ticket record", HttpStatus.UNAUTHORIZED);
        }


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
    public ResponseEntity<?> bookTicket(@RequestBody Map<String, Object> request) {
        try {
            int showtimeId = (int) request.get("showtimeId");
            double price = (double) request.get("price");
            long creditCardNumber = (long) request.get("creditCardNumber");
            String creditCardName = (String) request.get("creditCardName");
            int creditCardCV = (int) request.get("creditCardCV");
            String email = (String) request.get("email");
            List<Map<String, Object>> seats = (List<Map<String, Object>>) request.get("seats");

            Showtime showtime = showtimeService.getShowtimeById(showtimeId)
                    .orElseThrow(() -> new RuntimeException("Showtime not found for ID " + showtimeId));
            int movieId = showtime.getMovie().getMovieId();

            // List to hold seat objects
            List<Seat> seatList = new ArrayList<>();

            for (Map<String, Object> seatInfo : seats) {
                int seatNumber = (int) seatInfo.get("seatNumber");
                int seatRow = (int) seatInfo.get("seatRow");

                // Check if the seat is already booked
                if (seatService.isSeatBooked(showtimeId, seatNumber, seatRow)) {
                    throw new RuntimeException("Seat " + seatRow + "-" + seatNumber + " is already booked for showtime " + showtimeId);
                }

                // Create and associate the seat
                Seat seat = new Seat();
                seat.setSeatNumber(seatNumber);
                seat.setSeatRow(seatRow);
                seat.setShowtime(showtime); 
                seatList.add(seat); // Add to the list of seats
            }

            // Create the payment
            Payment payment = paymentService.createPayment(price, creditCardNumber, creditCardName, creditCardCV);

            // Book the ticket with the saved seats
            Ticket bookedTicket = ticketService.saveTicket(movieId, seatList, showtimeId, email, price);

            // Associate the ticket with each set and then save the seats
            // Associate the ticket with each seat and then save the seats
            for (Seat seat : seatList) {
                seat.setTicket(bookedTicket); 
            }
            seatService.saveAllSeats(seatList);


            // Save the receipt
            Receipt receipt = receiptService.createReceipt(email, payment.getPaymentId(), bookedTicket.getTicketId());

            // Return success with ticket ID
            return new ResponseEntity<>(bookedTicket.getTicketId(), HttpStatus.CREATED);

        } catch (Exception e) {
            // Handle any error
            return new ResponseEntity<>("Error processing the booking: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
