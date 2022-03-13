package com.example.diplom.services;


import com.example.diplom.entities.Examination;
import com.example.diplom.repositories.ExaminationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExaminationService {

    private final ExaminationRepository examinationRepository;

    public void save(Examination examination){
        examinationRepository.save(examination);
    }

    public List<Examination> findAll(){
        return examinationRepository.findAll();
    }

    public Examination findById(UUID id) {
        return examinationRepository.findById(id).get();
    }

    public void deleteById(UUID id) {
        examinationRepository.deleteById(id);
    }
}
