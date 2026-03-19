package com.psychotalk.service.publicService;

import com.psychotalk.exception.customExceptions.MailNotSentException;
import com.psychotalk.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(Email email) {

        if(email.getEmailAddress() == null || email.getMessage().isEmpty() || email.getMessage().length() < 10){
            throw new MailNotSentException("Invalid Message length or null email address");
        }

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("sagarchouhan2271705@gmail.com");
            message.setSubject(email.getSubject());
            String body = "New Contact Request\n\n"
                    + "Name: " + email.getSenderName() + "\n"
                    + "Email: " + email.getEmailAddress() + "\n"
                    + "Message: " + email.getMessage() + "\n"
                    + "Date: " + LocalDateTime.now();
            message.setText(body);
            message.setReplyTo(email.getEmailAddress());
            mailSender.send(message);
        }catch (Exception e){
            throw new MailNotSentException(e.getMessage());
        }
    }
}
