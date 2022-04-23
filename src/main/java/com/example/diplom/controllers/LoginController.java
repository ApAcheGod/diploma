package com.example.diplom.controllers;

import com.example.diplom.entities.User;
import com.example.diplom.entities.dto.LoginDto;
import com.example.diplom.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping(value = "/api/check", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getInfo(){
        return new ResponseEntity(SecurityContextHolder.getContext(), HttpStatus.OK);
    }

    @GetMapping("/resetPassword")
    public String changePassword(Principal principal, Model model){
        model.addAttribute("login", principal.getName());
        return "resetPasswordPage";
    }


    @PostMapping("/api/resetPassword")
    @ResponseBody
    public ResponseEntity changePassword(HttpServletRequest request, Principal principal){
        String password = request.getParameter("password");
        User user = userService.findByUserLogin(principal.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userService.save(user);
        return new ResponseEntity(user, HttpStatus.OK);
    }


}
