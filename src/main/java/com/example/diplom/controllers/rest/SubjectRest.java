package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Subject;
import com.example.diplom.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubjectRest {

    private final SubjectService subjectService;

    @GetMapping("/subjects")
    public List<Subject> allSubjects(){
        return subjectService.findAll();
    }

    @GetMapping("/subject/{id}")
    public Subject oneSubject(@PathVariable("id")UUID uuid){
        return subjectService.findById(uuid);
    }

    @PostMapping("/subject")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Subject subject) {
        subjectService.save(subject);
    }

    @PutMapping(value = "/subject/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Subject subject) {
        subjectService.save(subject);
    }

    @DeleteMapping(value = "subject/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        subjectService.deleteById(id);
    }
}
