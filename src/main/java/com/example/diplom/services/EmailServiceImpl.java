package com.example.diplom.services;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
//@ConditionalOnProperty
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("diplomaProject@yandex.ru");
        simpleMailMessage.setTo("nik.alpatov@mail.ru");
        simpleMailMessage.setSubject("Данные для входа");
        simpleMailMessage.setText("Привет это твои данные для входа");
        javaMailSender.send(simpleMailMessage);
    }
}
