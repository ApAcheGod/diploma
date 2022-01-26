package com.example.diplom.services;

import com.example.diplom.entities.Material;
import com.example.diplom.repositories.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    public void save(Material material){
        materialRepository.save(material);
    }

    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    public Material findByID(UUID uuid) {
        return materialRepository.getById(uuid);
    }
}
