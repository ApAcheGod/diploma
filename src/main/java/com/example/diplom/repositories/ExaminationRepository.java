package com.example.diplom.repositories;

import com.example.diplom.entities.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ExaminationRepository extends
        PagingAndSortingRepository<Examination, UUID>,
        JpaSpecificationExecutor<Examination>,
        JpaRepository<Examination, UUID> {
}
