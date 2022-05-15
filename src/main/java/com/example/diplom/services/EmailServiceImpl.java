package com.example.diplom.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
//@ConditionalOnProperty
public class EmailServiceImpl implements EmailService{

    private final String TEMPLATE = """
            Логин %s
            Пароль %s
            """;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String login, String password) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("diplomaProject@yandex.ru");
        simpleMailMessage.setTo("nik.alpatov@mail.ru");
        simpleMailMessage.setCc("mirvodartem@gmail.com");
        simpleMailMessage.setSubject("Данные для входа");
        simpleMailMessage.setText(String.format(TEMPLATE, login, password));
        javaMailSender.send(simpleMailMessage);
    }
}
