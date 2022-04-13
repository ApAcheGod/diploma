package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Student;
import com.example.diplom.entities.dto.StudentDto;
import com.example.diplom.repositories.RoleRepository;
import com.example.diplom.services.CreateLoginService;
import com.example.diplom.services.CreatePasswordService;
import com.example.diplom.services.EmailService;
import com.example.diplom.services.StudentService;
import com.example.diplom.services.mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudentRest {

    private final StudentService studentService;
    private final CreatePasswordService passwordService;
    private final RoleRepository roleRepository;
    private final CreateLoginService loginService;
    private final StudentMapper studentMapper;
    private final EmailService emailService;

    @GetMapping("/students")
    public List<StudentDto> allStudents(){
        return studentService.findAll().stream().map(studentMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/studentsWithoutGroup")
    public Set<StudentDto> studentsWithoutGroup(){
        return studentService.getAllWithoutGroup().stream().map(studentMapper::toDto).collect(Collectors.toSet());
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<StudentDto> oneStudent(@PathVariable("id")UUID uuid){
        return new ResponseEntity<>(studentMapper.toDto(studentService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping(value = "/student")
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        loginService.createLoginForUser(student);
        List<String> passwords = passwordService.createPassword();
        student.setPassword(passwords.get(1));
        student.setRoles(List.of(roleRepository.findRoleByRoleName("ROLE_STUDENT")));
        try{
            studentService.save(student);
            emailService.sendEmail("nil.alpatov@mail.ru", student.getLogin(), passwords.get(0));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(studentMapper.toDto(student),  HttpStatus.CREATED);
    }

    @PutMapping(value = "/student")
    public ResponseEntity<StudentDto> update(@RequestBody StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        Student source = studentService.findById(studentDto.getId());
        student.setPassword(source.getPassword());
        student.setLogin(source.getLogin());
        studentService.save(student);
        return new ResponseEntity<> (studentMapper.toDto(student), HttpStatus.OK);
    }

    @DeleteMapping(value = "/student/{id}")
    public void delete(@PathVariable("id") UUID id) {
        studentService.deleteById(id);
    }
}
