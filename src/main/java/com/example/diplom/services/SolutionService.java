package com.example.diplom.services;

import com.example.diplom.entities.Solution;
import com.example.diplom.repositories.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public void save(Solution solution){
        solutionRepository.save(solution);
    }
}
