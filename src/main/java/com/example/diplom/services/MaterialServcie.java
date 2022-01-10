package com.example.diplom.services;

import com.example.diplom.entities.Material;
import com.example.diplom.repositories.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialServcie {

    private final MaterialRepository materialRepository;

    public void save(Material material){
        materialRepository.save(material);
    }

    public List<Material> findAll() {
        return materialRepository.findAll();
    }
}
