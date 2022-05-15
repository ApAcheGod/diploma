package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Teacher;
import com.example.diplom.entities.dto.TeacherDto;
import com.example.diplom.repositories.RoleRepository;
import com.example.diplom.services.CreateLoginService;
import com.example.diplom.services.CreatePasswordService;
import com.example.diplom.services.EmailService;
import com.example.diplom.services.TeacherService;
import com.example.diplom.services.mappers.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final EmailService emailService;

    @GetMapping("/teachers")
    public List<TeacherDto> allTeachers(){
        return teacherService.findAll().stream().map(teacherMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<TeacherDto> oneTeacher(@PathVariable("id")UUID uuid){
        return new ResponseEntity<>(teacherMapper.toDto(teacherService.findById(uuid)), HttpStatus.OK);
    }

    @GetMapping("/teacherByLogin/{login}")
    public ResponseEntity<TeacherDto> getTeacherByLogin(@PathVariable("login") String login){
        return new ResponseEntity<>(teacherMapper.toDto(teacherService.findByLogin(login)), HttpStatus.OK);
    }

    @PostMapping("/teacher")
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherDto teacherDto) {
        Teacher teacher = teacherMapper.toEntity(teacherDto);
        loginService.createLoginForUser(teacher);
        List<String> passwords = passwordService.createPassword();
        teacher.setPassword(passwords.get(1));
        teacher.setRoles(List.of(roleRepository.findRoleByRoleName("ROLE_TEACHER")));
        try{
            teacherService.save(teacher);
            emailService.sendEmail("nik.alpatov@mail.ru", teacher.getLogin(), passwords.get(0));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(teacherMapper.toDto(teacher), HttpStatus.CREATED);
    }

    @PutMapping(value = "/teacher")
    public ResponseEntity<TeacherDto> update(@RequestBody TeacherDto teacherDto) {
        Teacher teacher = teacherMapper.toEntity(teacherDto);
        Teacher source = teacherService.findById(teacherDto.getId());
        teacher.setPassword(source.getPassword());
        teacher.setLogin(source.getLogin());
        teacherService.save(teacher);
        return new ResponseEntity<>(teacherMapper.toDto(teacher), HttpStatus.OK);
    }

    @DeleteMapping(value = "teacher/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") UUID id) {
        Teacher teacher = teacherService.findById(id);
        teacher.deleteLinks();
        teacherService.save(teacher);
        teacherService.deleteById(id);
    }
}
