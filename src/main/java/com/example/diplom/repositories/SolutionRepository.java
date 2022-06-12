package com.example.diplom.repositories;

import com.example.diplom.entities.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {

    @Query("select s from Solution s order by s.student.last_name")
    @Override
    List<Solution> findAll();
}
