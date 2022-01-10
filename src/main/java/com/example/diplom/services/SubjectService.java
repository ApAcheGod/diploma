package com.example.diplom.services;

import com.example.diplom.entities.Subject;
import com.example.diplom.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public void save(Subject subject){
        subjectRepository.save(subject);
    }

    public Object findAll() {
        return subjectRepository.findAll();
    }
}
