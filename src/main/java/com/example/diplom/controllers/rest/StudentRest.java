package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Student;
import com.example.diplom.entities.dto.StudentDTO;
import com.example.diplom.repositories.RoleRepository;
import com.example.diplom.services.CreateLoginService;
import com.example.diplom.services.CreatePasswordService;
import com.example.diplom.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/students")
    public List<Student> allStudents(){
        return studentService.findAll();
    }

    @GetMapping("/student/{id}")
    public Student oneStudent(@PathVariable("id")UUID uuid){
        return studentService.findById(uuid);
    }

    @PostMapping(value = "/student")
    @ResponseBody
    public ResponseEntity<Student> create(@RequestBody StudentDTO student) {
        Student s =  new Student();
        s.setFirst_name(student.getFirst_name());
        s.setLast_name(student.getLast_name());
        s.setPatronymic(student.getPatronymic());
        loginService.createLoginForUser(s);
        s.setPassword(passwordService.createPassword());
        s.setRoles(List.of(roleRepository.findRoleByRoleName("ROLE_STUDENT")));
        studentService.save(s);
        return new ResponseEntity<Student>(s,  HttpStatus.OK);
    }

    @PutMapping(value = "/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Student student) {
        studentService.save(student);
    }

    @DeleteMapping(value = "student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        studentService.deleteById(id);
    }
}
