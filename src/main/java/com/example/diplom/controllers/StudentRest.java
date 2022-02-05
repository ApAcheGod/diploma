package com.example.diplom.controllers;

import com.example.diplom.entities.Student;
import com.example.diplom.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudentRest {

    private final StudentService studentService;

    @GetMapping("/students")
    public List<Student> allStudents(){
        return studentService.findAll();
    }

    @GetMapping("/student/{id}")
    public Student oneStudent(@PathVariable("id")UUID uuid){
        return studentService.findById(uuid);
    }

    @PostMapping("/student") //-javaconfig
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Student student) {
        studentService.save(student);
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
