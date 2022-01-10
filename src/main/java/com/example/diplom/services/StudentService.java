package com.example.diplom.services;

import com.example.diplom.entities.Student;
import com.example.diplom.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public void save(Student student) {
        studentRepository.save(student);
    }
}
