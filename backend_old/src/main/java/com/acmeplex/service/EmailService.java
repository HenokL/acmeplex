package com.acmeplex.service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private JavaMailSender mailSender;

    // Constructor
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    // Sends email to the specified user
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("acmeplex94@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);

        System.out.println("Mail sent sucessfully");
    }

}