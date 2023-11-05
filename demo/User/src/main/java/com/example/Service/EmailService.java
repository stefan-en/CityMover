package com.example.Service;

import com.example.interfaces.EmailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {
    private final JavaMailSender emailSender;
    @Override
    public void sendEmailAlert(String email, Integer cod) {
        if(email != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("test.test.licenta14@gmail.com");
            message.setTo(email);
            message.setSubject("Codul tau de resetare");
            message.setText("Codul tau de resetare pentru parola din cadrul aplicatiei City Moving este: " + cod + ".");
            emailSender.send(message);
        }

    }
    public EmailService(JavaMailSender emailSender){

        this.emailSender = emailSender;
    }

}
