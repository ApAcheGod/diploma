package com.example.diplom.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "diploma")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String first_name;

    private String last_name;

    private String patronymic;

    private String password;

//    @Email
//    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    private String login;


}
