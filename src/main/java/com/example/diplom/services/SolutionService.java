package com.example.diplom.services;

import com.example.diplom.entities.Solution;
import com.example.diplom.repositories.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public void save(Solution solution){
        solutionRepository.save(solution);
    }

    public List<Solution> findAll() {
        return solutionRepository.findAll();
    }

    public Solution findById(UUID uuid) {
        return solutionRepository.getById(uuid);
    }

    public void deleteById(UUID uuid) {
        solutionRepository.deleteById(uuid);
    }
}
