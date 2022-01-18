package com.example.diplom.repositories;

import com.example.diplom.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
//    @Query("select count(u) from User u where u.first_name = ?1 and u.last_name = ?2 and u.patronymic = ?3")
//    int countUserByFirst_nameAndAndLast_nameAndAndPatronymic(String first_name, String last_name, String patronymic);

    @Query("select count(u) from User u where u.first_name = ?1 and u.last_name = ?2 and u.patronymic = ?3")
    int countUsersByFirst_nameAndAndLast_nameAndPatronymic(String first_name, String last_name, String patronymic);

//    @Query("select count(u) from User u where u.first_name = ?1")
//    int countUsersByFirst_name(String first_name);

    User findUserByLogin(String login);
}
