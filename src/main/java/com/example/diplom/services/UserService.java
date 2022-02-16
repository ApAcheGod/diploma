package com.example.diplom.services;

import com.example.diplom.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService{// extends UserDetailsService{
    User findByUserLogin(String login);
}
