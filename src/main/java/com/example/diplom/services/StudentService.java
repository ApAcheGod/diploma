package com.example.diplom.services;

import com.example.diplom.entities.Student;
import com.example.diplom.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public void save(Student student) {
        studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(UUID uuid) {
        return studentRepository.getById(uuid);
    }

    @Transactional
    public void deleteById(UUID uuid) {
        studentRepository.deleteById(uuid);
    }

    public Student findByLogin(String login) {
        return studentRepository.findStudentByLogin(login);
    }

    public Set<Student> getAllWithoutGroup(){
        return studentRepository.findAllByGroupIsNull();
    }
}
