package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Solution;
import com.example.diplom.entities.dto.SolutionDto;
import com.example.diplom.services.SolutionService;
import com.example.diplom.services.mappers.SolutionMapper;
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
public class SolutionRest {

    private final SolutionService solutionService;
    private final SolutionMapper solutionMapper;

    @GetMapping("/solutions")
    public List<SolutionDto> allSolutions(){
        return solutionService.findAll().stream().map(solutionMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/solution/{id}")
    public ResponseEntity<SolutionDto> oneSolution(@PathVariable("id") UUID uuid){
        return new ResponseEntity<>( solutionMapper.toDto(solutionService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/solution")
    public ResponseEntity<SolutionDto> create(@RequestBody SolutionDto solutionDto) {
        Solution solution = solutionMapper.toEntity(solutionDto);
        solutionService.save(solution);
        return new ResponseEntity<>(solutionMapper.toDto(solution), HttpStatus.CREATED);
    }

    @PutMapping("/solution")
    public ResponseEntity<SolutionDto> update(@RequestBody SolutionDto solutionDto) {
        Solution solution = solutionMapper.toEntity(solutionDto);
        solutionService.save(solution);
        return new ResponseEntity<>(solutionMapper.toDto(solution), HttpStatus.OK);
    }

    @DeleteMapping("solution/{id}")
    public void delete(@PathVariable("id") UUID id) {
        solutionService.deleteById(id);
    }
}
