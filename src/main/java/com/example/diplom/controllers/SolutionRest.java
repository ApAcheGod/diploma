package com.example.diplom.controllers;

import com.example.diplom.entities.Solution;
import com.example.diplom.services.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SolutionRest {

    private final SolutionService solutionService;

    @GetMapping("/solutions")
    public List<Solution> allSolutions(){
        return solutionService.findAll();
    }

    @GetMapping("/solution/{id}")
    public Solution oneSolution(@PathVariable("id") UUID uuid){
        return solutionService.findById(uuid);
    }

    @PostMapping("/solution")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Solution solution) {
        solutionService.save(solution);
    }

    @PutMapping("/solution/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Solution solution) {
        solutionService.save(solution);
    }

    @DeleteMapping("solution/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        solutionService.deleteById(id);
    }
}
