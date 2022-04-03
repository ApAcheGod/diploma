package com.example.diplom.controllers.rest;

import com.example.diplom.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/support")
public class SupportController {

    private final EmailService emailService;

    @GetMapping("/sendEmail")
    public void sendEmail(){
        emailService.sendEmail("123", "message");
    }
}
