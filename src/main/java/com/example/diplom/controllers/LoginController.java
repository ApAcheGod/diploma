package com.example.diplom.controllers;

import com.example.diplom.entities.User;
import com.example.diplom.entities.dto.LoginDto;
import com.example.diplom.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/loginRest")
    @ResponseBody
    public ResponseEntity login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.login(), loginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(authentication.getPrincipal(), HttpStatus.OK);
    }

    @GetMapping("/loginCheck")
    @ResponseBody
    public ResponseEntity getInfo(){
        return new ResponseEntity(SecurityContextHolder.getContext().getAuthentication().getPrincipal() ,HttpStatus.OK);
    }

    @GetMapping("/logoutCheck")
    @ResponseBody
    public ResponseEntity logoutUser(){
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/resetPassword")
    public String changePassword(Principal principal, Model model){
        model.addAttribute("login", principal.getName());
        return "resetPasswordPage";
    }


    @PostMapping("/resetPassword")
    @ResponseBody
    public ResponseEntity changePassword(HttpServletRequest request, Principal principal){
        String password = request.getParameter("password");
        User user = userService.findByUserLogin(principal.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userService.save(user);
        return new ResponseEntity(user, HttpStatus.OK);
    }

//    @PostMapping("/login")
//    public String loginUser(@RequestParam(name = "error", required = false) String error,
//                            @RequestParam(name = "logout", required = false) String logout,
//                            Model model) {
//        return "redirect:main";
//    }


}
