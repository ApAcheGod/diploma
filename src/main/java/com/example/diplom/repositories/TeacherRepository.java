package com.example.diplom.repositories;

import com.example.diplom.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    Teacher findTeacherByLogin(String login);

    @Query("select t from Teacher t order by t.last_name")
    @Override
    List<Teacher> findAll();
}
