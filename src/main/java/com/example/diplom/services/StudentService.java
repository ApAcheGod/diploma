package com.example.diplom.services;

import com.example.diplom.entities.Student;
import com.example.diplom.repositories.RoleRepository;
import com.example.diplom.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CreatePasswordService passwordService;
    private final RoleRepository roleRepository;
    private final CreateLoginService loginService;

    public void save(Student student) {
        studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

}
