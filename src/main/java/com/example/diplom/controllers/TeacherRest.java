package com.example.diplom.controllers;

import com.example.diplom.entities.Teacher;
import com.example.diplom.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeacherRest {

    private final TeacherService teacherService;

    @GetMapping("/teachers")
    public List<Teacher> allTeachers(){
        return teacherService.findAll();
    }

    @GetMapping("/teacher/{id}")
    public Teacher oneTeacher(@PathVariable("id")UUID uuid){
        return teacherService.findById(uuid);
    }

    @PostMapping("/teacher")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
    }

    @PutMapping(value = "/teacher/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Teacher teacher) {
        teacherService.save(teacher);
    }

    @DeleteMapping(value = "teacher/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        teacherService.deleteById(id);
    }
}
