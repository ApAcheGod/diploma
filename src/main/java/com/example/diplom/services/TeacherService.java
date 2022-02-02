package com.example.diplom.services;

import com.example.diplom.entities.Teacher;
import com.example.diplom.repositories.RoleRepository;
import com.example.diplom.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final CreatePasswordService passwordService;
    private final RoleRepository roleRepository;
    private final CreateLoginService loginService;

    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher findById(UUID uuid){
        return teacherRepository.getById(uuid);
    }

    public Teacher findByLogin(String login){
        return teacherRepository.findTeacherByLogin(login);
    }

    public void deleteById(UUID uuid) {
        teacherRepository.deleteById(uuid);
    }
}
