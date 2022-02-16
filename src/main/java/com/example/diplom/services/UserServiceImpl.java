package com.example.diplom.services;

import com.example.diplom.entities.Role;
import com.example.diplom.entities.User;
import com.example.diplom.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public int numberOfBodies(User user) {
        return userRepository.countUsersByFirst_nameAndAndLast_nameAndPatronymic(
                user.getFirst_name(),
                user.getLast_name(),
                user.getPatronymic());
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findUserByLogin(username);
//        if (user == null){
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRoles())
//        );
//    }

    @Override
    public User findByUserLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
//                .collect(Collectors.toList());
//    }
}
