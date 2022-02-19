package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Subject;
import com.example.diplom.entities.dto.SubjectDto;
import com.example.diplom.services.SubjectService;
import com.example.diplom.services.mappers.SubjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubjectRest {

    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;
    @GetMapping("/subjects")
    public List<Subject> allSubjects(){
        return subjectService.findAll();
    }

    @GetMapping("/subject/{id}")
    public ResponseEntity<SubjectDto> oneSubject(@PathVariable("id")UUID uuid){
        return new ResponseEntity<>(subjectMapper.toDto(subjectService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/subject")
    public ResponseEntity<Subject> create(@RequestBody SubjectDto subjectDto) {
        Subject subject = subjectMapper.toEntity(subjectDto);
        subjectService.save(subject);
        return new ResponseEntity<>(subject, HttpStatus.CREATED);
    }

    @PutMapping(value = "/subject/{id}")
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Subject subject) {
        subjectService.save(subject);
    }

    @DeleteMapping(value = "subject/{id}")
    public void delete(@PathVariable("id") UUID id) {
        subjectService.deleteById(id);
    }
}
