package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Teacher;
import com.example.diplom.entities.dto.TeacherDto;
import com.example.diplom.services.CreateLoginService;
import com.example.diplom.services.CreatePasswordService;
import com.example.diplom.services.TeacherService;
import com.example.diplom.services.mappers.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeacherRest {

    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;
    private final CreateLoginService loginService;
    private final CreatePasswordService passwordService;

    @GetMapping("/teachers")
    public List<Teacher> allTeachers(){
        return teacherService.findAll();
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<TeacherDto> oneTeacher(@PathVariable("id")UUID uuid){
        return new ResponseEntity<>(teacherMapper.toDto(teacherService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/teacher")
    public ResponseEntity<Teacher> create(@RequestBody TeacherDto teacherDto) {
        Teacher teacher = teacherMapper.toEntity(teacherDto);
        loginService.createLoginForUser(teacher);
        teacher.setPassword(passwordService.createPassword());
        teacherService.save(teacher);
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @PutMapping(value = "/teacher/{id}")
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Teacher teacher) {
        teacherService.save(teacher);
    }

    @DeleteMapping(value = "teacher/{id}")
    public void delete(@PathVariable("id") UUID id) {
        teacherService.deleteById(id);
    }
}
