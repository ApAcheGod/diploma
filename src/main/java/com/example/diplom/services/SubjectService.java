package com.example.diplom.services;

import com.example.diplom.entities.Subject;
import com.example.diplom.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public void save(Subject subject){
        subjectRepository.save(subject);
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject findById(UUID uuid) {
        return subjectRepository.getById(uuid);
    }

    public void deleteById(UUID uuid) {
        subjectRepository.deleteById(uuid);
    }
}
