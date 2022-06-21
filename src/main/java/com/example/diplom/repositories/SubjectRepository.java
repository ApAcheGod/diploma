package com.example.diplom.repositories;

import com.example.diplom.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    @Transactional
    @Modifying
    @Query("delete from Subject g where g.id = ?1")
    void deleteById(UUID uuid);

    @Query("select s from Subject s order by s.name")
    @Override
    List<Subject> findAll();
}
