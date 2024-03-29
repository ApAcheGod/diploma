package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Examination;
import com.example.diplom.entities.dto.ExaminationDto;
import com.example.diplom.services.ExaminationService;
import com.example.diplom.services.mappers.ExaminationMapper;
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
public class ExaminationRest {

    private final ExaminationService examinationService;
    private final ExaminationMapper examinationMapper;

    @GetMapping("/examinations")
    public List<ExaminationDto> allExaminations(){
        return examinationService.findAll().stream().map(examinationMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/examination/{id}")
    public ResponseEntity<ExaminationDto> oneExamination(@PathVariable("id") UUID uuid){
        return new ResponseEntity<>(examinationMapper.toDto(examinationService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/examination")
    public ResponseEntity<ExaminationDto> create(@RequestBody ExaminationDto examinationDto) {
        Examination examination = examinationMapper.toEntity(examinationDto);
        examinationService.save(examination);
        return new ResponseEntity<>(examinationMapper.toDto(examination), HttpStatus.CREATED);
    }

    @PutMapping("/examination")
    public ResponseEntity<ExaminationDto> update(@RequestBody ExaminationDto examinationDto) {
        Examination examination = examinationMapper.toEntity(examinationDto);
        examinationService.save(examination);
        return new ResponseEntity<>(examinationMapper.toDto(examination), HttpStatus.OK);
    }

    @DeleteMapping("examination/{id}")
    public void delete(@PathVariable("id") UUID id) {
        examinationService.deleteById(id);
    }
}
