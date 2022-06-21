package com.example.diplom.repositories;

import com.example.diplom.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    Student findStudentByLogin(String login);

    Set<Student> findAllByGroupIsNull();

    @Query("select s from Student s order by s.last_name")
    @Override
    List<Student> findAll();
}
