package com.acmeplex.controller;
import com.acmeplex.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;  // Using constructor-based injection

    // Constructor-based dependency injection
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String to, 
                            @RequestParam String subject, 
                            @RequestParam String body) {
        try {
            emailService.sendEmail(to, subject, body);
            return "Email sent successfully!";
        } catch (Exception e) {
            // Log the error (you can use a logger like slf4j here)
            return "Failed to send email: " + e.getMessage();
        }
    }
}
