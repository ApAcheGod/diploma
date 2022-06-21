package com.example.diplom.repositories;

import com.example.diplom.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MaterialRepository extends JpaRepository<Material, UUID> {

    @Query("select m from Material m order by m.name")
    @Override
    List<Material> findAll();
}
