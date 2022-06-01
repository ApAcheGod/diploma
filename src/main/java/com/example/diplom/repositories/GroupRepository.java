package com.example.diplom.repositories;

import com.example.diplom.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    @Transactional
    @Modifying
    @Query("delete from Group g where g.id = ?1")
    void deleteById(UUID uuid);
}

