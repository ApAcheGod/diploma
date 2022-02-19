package com.example.diplom.controllers;

import com.example.diplom.entities.Student;
import com.example.diplom.entities.dto.StudentDto;
import com.example.diplom.repositories.RoleRepository;
import com.example.diplom.services.CreateLoginService;
import com.example.diplom.services.CreatePasswordService;
import com.example.diplom.services.StudentService;
import com.example.diplom.services.mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudentRest {

    private final StudentService studentService;
    private final CreatePasswordService passwordService;
    private final RoleRepository roleRepository;
    private final CreateLoginService loginService;
    private final StudentMapper studentMapper;

    @GetMapping("/students")
    public List<Student> allStudents(){
        return studentService.findAll();
    }

    @GetMapping("/student/{id}")
    public Student oneStudent(@PathVariable("id")UUID uuid){
        return studentService.findById(uuid);
    }

    @PostMapping(value = "/student")
    public ResponseEntity<Student> create(@RequestBody StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        loginService.createLoginForUser(student);
        student.setPassword(passwordService.createPassword());
        student.setRoles(List.of(roleRepository.findRoleByRoleName("ROLE_STUDENT")));
        studentService.save(student);
        return new ResponseEntity<Student>(student,  HttpStatus.OK);
    }

    @PutMapping(value = "/student/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> update(@PathVariable( "uuid" ) UUID uuid, @RequestBody StudentDto studentDto) {

        System.out.println(uuid);
        System.out.println(studentDto);
        Student student = studentMapper.toEntity(studentDto);
        studentService.save(student);
        return new ResponseEntity<StudentDto> (studentMapper.toDto(student), HttpStatus.OK);
    }

    @DeleteMapping(value = "student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        studentService.deleteById(id);
    }
}
