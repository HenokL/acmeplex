package com.acmeplex.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * The EmailService class is responsible for sending emails using the JavaMailSender.
 * This service uses simple email functionality, allowing the application to send basic
 * text-based emails (without attachments or advanced features).
 * Author: Riley Koppang
 */
@Service
public class EmailService {

    // JavaMailSender is injected to enable sending emails
    private final JavaMailSender mailSender; 

    /**
     * Constructor-based dependency injection for JavaMailSender.
     * This ensures that the JavaMailSender instance is provided by the Spring container.
     * 
     * @param mailSender The JavaMailSender instance that will be used to send emails.
     */
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a simple email to the specified recipient.
     * This method creates an email message with the provided recipient, subject, and body,
     * and then uses the JavaMailSender to send it.
     * 
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param body The content (body) of the email.
     */
    public void sendEmail(String to, String subject, String body) {
        // Create a new SimpleMailMessage object to represent the email
        SimpleMailMessage message = new SimpleMailMessage();

        // Set the 'From' address for the email
        message.setFrom("acmeplex94@gmail.com");

        // Set the recipient's address
        message.setTo(to);

        // Set the subject of the email
        message.setSubject(subject);

        // Set the body of the email
        message.setText(body);

        try {
            // Send the email using the mailSender instance
            mailSender.send(message);
            System.out.println("Mail sent successfully");
        } catch (MailException e) {
            // Handle any exceptions that occur when sending the email (e.g., log error)
            System.err.println("Error sending email: " + e.getMessage());
        }
    }


    /**
     * Sends a cancellation email to the specified recipient.
     * This method creates an email message with the provided recipient's email address, 
     * a subject indicating the cancellation, and a body containing information about the ticket cancellation.
     * It then uses the JavaMailSender to send the email.
     * 
     * @param to The recipient's email address (e.g., the user whose ticket has been cancelled).
     * @param ticketId The unique identifier for the cancelled ticket.
     */
    public void sendCancellationEmail(String to, String ticketId) {
        // Define the subject of the cancellation email
        String subject = "Ticket Cancellation Confirmation";
    
        // Define the body of the cancellation email
        String body = "Dear User,\n\n"
                + "We are sorry to inform you that your ticket with ID " + ticketId + " has been successfully cancelled.\n"
                + "If you have any questions or need assistance, please don't hesitate to contact us.\n\n"
                + "Best regards,\n"
                + "The Ticketing Team";
    
        // Call the sendEmail method with the provided recipient, subject, and body
        sendEmail(to, subject, body);
    }
    


}
