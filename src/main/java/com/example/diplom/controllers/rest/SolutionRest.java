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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SolutionRest {

    private final SolutionService solutionService;
    private final SolutionMapper solutionMapper;
    @GetMapping("/solutions")
    public List<Solution> allSolutions(){
        return solutionService.findAll();
    }

    @GetMapping("/solution/{id}")
    public ResponseEntity<SolutionDto> oneSolution(@PathVariable("id") UUID uuid){
        return new ResponseEntity<>( solutionMapper.toDto(solutionService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/solution")
    public ResponseEntity<Solution> create(@RequestBody SolutionDto solutionDto) {
        Solution solution = solutionMapper.toEntity(solutionDto);
        solutionService.save(solution);
        return new ResponseEntity<>(solution, HttpStatus.CREATED);
    }

    @PutMapping("/solution/{id}")
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Solution solution) {
        solutionService.save(solution);
    }

    @DeleteMapping("solution/{id}")
    public void delete(@PathVariable("id") UUID id) {
        solutionService.deleteById(id);
    }
}
