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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubjectRest {

    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @GetMapping("/subjects")
    public List<SubjectDto> allSubjects(){
        return subjectService.findAll().stream().map(subjectMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/subject/{id}")
    public ResponseEntity<SubjectDto> oneSubject(@PathVariable("id")UUID uuid){
        return new ResponseEntity<>(subjectMapper.toDto(subjectService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/subject")
    public ResponseEntity<SubjectDto> create(@RequestBody SubjectDto subjectDto) {
        Subject subject = subjectMapper.toEntity(subjectDto);
        subjectService.save(subject);
        return new ResponseEntity<>(subjectMapper.toDto(subject), HttpStatus.CREATED);
    }

    @PutMapping(value = "/subject")
    public ResponseEntity<SubjectDto> update(@RequestBody SubjectDto subjectDto) {
        Subject subject1 = subjectService.findById(subjectDto.getId());
        subject1.deleteLinks();
        subjectService.save(subject1);
        System.out.println("saved");
        Subject subject = subjectMapper.toEntity(subjectDto);
        subjectService.save(subject);
        return new ResponseEntity<>(subjectMapper.toDto(subject), HttpStatus.OK);
    }

    @DeleteMapping(value = "subject/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        Subject subject = subjectService.findById(id);
        subject.deleteLinks();
        subjectService.save(subject);
        subjectService.deleteById(id);
    }
}
