package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Teacher;
import com.example.diplom.entities.dto.TeacherDto;
import com.example.diplom.repositories.RoleRepository;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeacherRest {

    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;
    private final CreateLoginService loginService;
    private final RoleRepository roleRepository;
    private final CreatePasswordService passwordService;

    @GetMapping("/teachers")
    public List<TeacherDto> allTeachers(){
        return teacherService.findAll().stream().map(teacherMapper::toDto).collect(Collectors.toList());
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
        teacher.setRoles(List.of(roleRepository.findRoleByRoleName("ROLE_TEACHER")));
        teacherService.save(teacher);
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @PutMapping(value = "/teacher")
    public ResponseEntity<TeacherDto> update(@RequestBody TeacherDto teacherDto) {
        Teacher teacher = teacherService.findById(teacherDto.getId());
        teacher.setFirst_name(teacherDto.getFirst_name());
        teacher.setLast_name(teacherDto.getLast_name());
        teacher.setPatronymic(teacherDto.getPatronymic());
        teacher.setEmail(teacherDto.getEmail());
        teacherService.save(teacher);
        return new ResponseEntity<>(teacherMapper.toDto(teacher), HttpStatus.OK);
    }

    @DeleteMapping(value = "teacher/{id}")
    public void delete(@PathVariable("id") UUID id) {
        teacherService.deleteById(id);
    }

    @GetMapping(value = "/testMethod")
    public ResponseEntity<Teacher> testMethod(@RequestBody TeacherDto teacherDto) {
        return new ResponseEntity<>(teacherMapper.toEntity(teacherDto), HttpStatus.OK);
    }

}
