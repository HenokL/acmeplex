package com.acmeplex.service;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service 
public class EmailService {

    private final JavaMailSender mailSender; 

    // Constructor-based injection
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Method to send the email
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("acmeplex94@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        try {
            mailSender.send(message);  // Sends the email
            System.out.println("Mail sent successfully");
        } catch (MailException e) {
            // Handle failure (logging, rethrowing, etc.)
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
