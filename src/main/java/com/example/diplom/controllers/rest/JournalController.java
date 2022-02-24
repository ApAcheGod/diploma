package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Journal;
import com.example.diplom.services.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JournalController {

    private final JournalService journalService;

    @GetMapping("/journals")
    public ResponseEntity getAllJournals(){
        return new ResponseEntity<>(
                journalService
                        .findAll()
                        .stream()
//                        .collect(Collectors.toMap(Journal::getGroupName, Function.identity())),
                        .collect(Collectors.groupingBy(Journal::getGroupName)),

                HttpStatus.OK);
    }

    @GetMapping("/journals/teacher/{login}")
    public ResponseEntity getTeacherJournals(@PathVariable("login") String login) {
        return new ResponseEntity<>(
                journalService
                        .findAll()
                        .stream()
                        .filter(j -> j.getTeacherLogin().equals(login))
//                        .collect(Collectors.toMap(Journal::getGroupName, Function.identity())),
                        .collect(Collectors.groupingBy(Journal::getGroupName)),
                HttpStatus.OK);
    }

    @GetMapping("/journals/student/{login}")
    public ResponseEntity getStudentJournals(@PathVariable("login") String login) {
        return new ResponseEntity<>(
                journalService
                        .findAll()
                        .stream()
                        .filter(j -> j.getStudentLogin().equals(login))
                        .collect(Collectors.toList()), HttpStatus.OK);
    }
}
