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
import com.acmeplex.service.RegisteredUserService;
import com.acmeplex.service.EmailService;
import com.acmeplex.service.CreditService;
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

/**
 * TicketController manages all ticket-related HTTP requests including ticket retrieval,
 * booking, and cancellation.
 * Author: Riley Koppang
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ShowtimeService showtimeService;
    private final PaymentService paymentService;
    private final SeatService seatService;
    private final ReceiptService receiptService;
    private final RegisteredUserService registeredUserService;
    private final CreditService creditService;
    private final EmailService emailService;


    /**
     * Constructor-based dependency injection to initialize required services.
     *
     * @param ticketService Service for ticket-related operations.
     * @param showtimeService Service for managing showtimes.
     * @param paymentService Service for payment-related operations.
     * @param seatService Service for seat-related operations.
     * @param receiptService Service for receipt-related operations.
     * @param registeredUserService Service for managing registered user data.
     * @param creditService Service for credit management.
     */
    public TicketController(TicketService ticketService, ShowtimeService showtimeService, PaymentService paymentService, SeatService seatService, ReceiptService receiptService, RegisteredUserService registeredUserService, CreditService creditService, EmailService emailService) {
        this.ticketService = ticketService;
        this.showtimeService = showtimeService;
        this.paymentService = paymentService;
        this.seatService = seatService;
        this.receiptService = receiptService;
        this.registeredUserService = registeredUserService;
        this.creditService = creditService;
        this.emailService = emailService;
    }

    /**
     * Retrieves a ticket by its ID.
     *
     * @param ticketId The ID of the ticket to retrieve.
     * @return The ticket if found, with HTTP status 200 (OK), or 404 (Not Found) if not found.
     */
    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        if (ticket != null) {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all tickets.
     *
     * @return A list of all tickets with HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    /**
     * Cancels a ticket based on ticket ID and email verification.
     *
     * @param ticketId The ID of the ticket to cancel.
     * @param request A map containing the email of the user requesting cancellation.
     * @return Success or failure message with appropriate HTTP status.
     */
    @PutMapping("/cancel/{ticketId}")
    public ResponseEntity<String> cancelTicket(@PathVariable int ticketId, @RequestBody Map<String, String> request) {

        // Fetch the ticket using the provided ticket ID
        Ticket ticket = ticketService.getTicketById(ticketId);

        if (ticket == null) {
            return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
        }

        // Check if the ticket has already been canceled
        if (ticket.getStatus().equals("Cancelled")) {
            return new ResponseEntity<>("Ticket is already cancelled", HttpStatus.BAD_REQUEST);
        }

        // Get the showtime and check the date
        Showtime showtime = ticket.getShowtime();
        LocalDate showtimeDate = showtime.getShowtimeDate().toLocalDate();
        LocalDate now = LocalDate.now();
        long daysBetween = Duration.between(now.atStartOfDay(), showtimeDate.atStartOfDay()).toDays();

        // Prevent cancellation if showtime is within 3 days
        if (daysBetween <= 3) {
            return new ResponseEntity<>("Cannot cancel ticket within 3 days of the showtime", HttpStatus.BAD_REQUEST);
        }

        // Check if the email matches the one stored in the ticket
        String email = request.get("email");
        if (!email.equals(ticket.getEmail())) {
            return new ResponseEntity<>("Email does not match the ticket record", HttpStatus.UNAUTHORIZED);
        }

        // Proceed with ticket cancellation
        ticketService.cancelTicket(ticketId, email);

        // Send an email that their ticket is cancelled
        emailService.sendCancellationEmail(email, String.valueOf(ticketId));


        return new ResponseEntity<>("Ticket deleted successfully", HttpStatus.OK);
    }

    /**
     * Books a ticket for a showtime, processes payment, and generates a receipt.
     *
     * @param request A map containing ticket booking details such as showtime, price, credit card information, and seats.
     * @return The ID of the booked ticket or an error message with HTTP status.
     */
    @PostMapping("/book")
    public ResponseEntity<?> bookTicket(@RequestBody Map<String, Object> request) {
        try {
            // Extract information from the request body
            int showtimeId = (int) request.get("showtimeId");
            Object priceObject = request.get("price");
            String creditCardNumber = (String) request.get("creditCardNumber");
            String creditCardName = (String) request.get("creditCardName");
            String creditCardCV = (String) request.get("creditCardCV");
            String creditCardExpiryDate = (String) request.get("creditCardExpiryDate");
            String email = (String) request.get("email");
            List<Map<String, Object>> seats = (List<Map<String, Object>>) request.get("seats");
            Object creditsUsedObject = request.get("creditsUsed");

            // Ensure that the showtime exists
            Showtime showtime = showtimeService.getShowtimeById(showtimeId)
                    .orElseThrow(() -> new RuntimeException("Showtime not found for ID " + showtimeId));

            // Process credits used
            Double creditsUsed = convertToDouble(creditsUsedObject);

            // Process price
            double price = convertToDouble(priceObject);

            // Create seats and check for conflicts
            List<Seat> seatList = createSeats(seats, showtime);

            // Process payment
            Payment payment = paymentService.createPayment(price, creditCardNumber, creditCardName, creditCardCV, creditsUsed);

            // Deduct credits if used
            if (creditsUsed > 0.0) {
                creditService.issueCredits(email, -creditsUsed);
            }

            // Update credit card info for registered user
            registeredUserService.updateCreditCardInfo(email, creditCardNumber, creditCardCV, creditCardExpiryDate);

            // Save ticket and associate it with seats
            Ticket bookedTicket = ticketService.saveTicket(showtime.getMovie().getMovieId(), seatList, showtimeId, email, price);
            saveSeatsWithTicket(seatList, bookedTicket);

            // Generate receipt
            Receipt receipt = receiptService.createReceipt(email, payment.getPaymentId(), bookedTicket.getTicketId());

            // Return the ticket ID on successful booking
            return new ResponseEntity<>(bookedTicket.getTicketId(), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Error processing the booking: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Converts an object to a double, ensuring it can be safely processed.
     *
     * @param object The object to convert.
     * @return The object converted to a double.
     */
    private Double convertToDouble(Object object) {
        if (object instanceof Integer) {
            return ((Integer) object).doubleValue();
        } else if (object instanceof Float) {
            return ((Float) object).doubleValue();
        } else if (object instanceof Double) {
            return (Double) object;
        } else {
            throw new IllegalArgumentException("Invalid type for value");
        }
    }

    /**
     * Creates seats from the provided list of seat data.
     *
     * @param seats The list of seat data to create seats.
     * @param showtime The showtime the seats are associated with.
     * @return A list of created seats.
     */
    private List<Seat> createSeats(List<Map<String, Object>> seats, Showtime showtime) {
        List<Seat> seatList = new ArrayList<>();
        for (Map<String, Object> seatInfo : seats) {
            int seatNumber = (int) seatInfo.get("seatNumber");
            int seatRow = (int) seatInfo.get("seatRow");

            if (seatService.isSeatBooked(showtime.getShowtimeId(), seatNumber, seatRow)) {
                throw new RuntimeException("Seat " + seatRow + "-" + seatNumber + " is already booked");
            }

            Seat seat = new Seat();
            seat.setSeatNumber(seatNumber);
            seat.setSeatRow(seatRow);
            seat.setShowtime(showtime);
            seatList.add(seat);
        }
        return seatList;
    }

    /**
     * Associates the saved seats with the booked ticket and saves them.
     *
     * @param seatList The list of seats to be saved.
     * @param bookedTicket The ticket to associate with the seats.
     */
    private void saveSeatsWithTicket(List<Seat> seatList, Ticket bookedTicket) {
        for (Seat seat : seatList) {
            seat.setTicket(bookedTicket);
        }
        seatService.saveAllSeats(seatList);
    }
}
