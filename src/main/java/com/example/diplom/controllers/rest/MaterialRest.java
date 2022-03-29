package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Material;
import com.example.diplom.entities.dto.MaterialDto;
import com.example.diplom.services.MaterialService;
import com.example.diplom.services.mappers.MaterialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MaterialRest {

    private final MaterialService materialService;
    private final MaterialMapper materialMapper;

    @GetMapping("/materials")
    public List<MaterialDto> allMaterials(){
        return materialService.findAll().stream().map(materialMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/material/{id}")
    public ResponseEntity<MaterialDto> oneMaterials(@PathVariable("id") UUID uuid){
        return new ResponseEntity<>(materialMapper.toDto(materialService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/material")
    public ResponseEntity<Material> create(@RequestBody MaterialDto materialDto) {
        Material material = materialMapper.toEntity(materialDto);
        materialService.save(material);
        return new ResponseEntity<>(material, HttpStatus.CREATED);
    }

    @PutMapping("/material/{id}")
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Material material) {
        materialService.save(material);
    }

    @DeleteMapping("material/{id}")
    public void delete(@PathVariable("id") UUID id) {
        materialService.deleteById(id);
    }
}
