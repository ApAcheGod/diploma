package com.example.diplom.controllers;

import com.example.diplom.entities.Student;
import com.example.diplom.repositories.RoleRepository;
import com.example.diplom.services.CreateLoginService;
import com.example.diplom.services.CreatePasswordService;
import com.example.diplom.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @PostMapping(value = "/student"
            ,
            consumes = MediaType.APPLICATION_JSON_VALUE
            ,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers = "Accept=application/json"
    ) //-javaconfig
//    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Student> create(@RequestBody Map<String, Object> transactionalMap) {
        Student s =  new Student();
        s.setFirst_name(transactionalMap.get("first_name").toString());
        s.setLast_name(transactionalMap.get("last_name").toString());
        s.setPatronymic(transactionalMap.get("patronymic").toString());
        loginService.createLoginForUser(s);
        s.setPassword(passwordService.createPassword());
        s.setRoles(List.of(roleRepository.findRoleByRoleName("ROLE_STUDENT")));
        studentService.save(s);
//        studentService.save(student);
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
