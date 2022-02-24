package com.example.diplom.services;

import com.example.diplom.entities.Journal;
import com.example.diplom.repositories.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;

//    public Page<Journal> findAll(Pageable pageable){
//        return journalRepository.findAll(pageable);
//    }

    public List<Journal> findAll(){
        return journalRepository.findAll();
    }
}
