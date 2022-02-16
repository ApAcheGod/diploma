package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Material;
import com.example.diplom.services.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MaterialRest {

    private final MaterialService materialService;

    @GetMapping("/materials")
    public List<Material> allMaterials(){
        return materialService.findAll();
    }

    @GetMapping("/material/{id}")
    public Material oneMaterials(@PathVariable("id") UUID uuid){
        return materialService.findById(uuid);
    }

    @PostMapping("/material")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Material material) {
        materialService.save(material);
    }

    @PutMapping("/material/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Material material) {
        materialService.save(material);
    }

    @DeleteMapping("material/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        materialService.deleteById(id);
    }
}
