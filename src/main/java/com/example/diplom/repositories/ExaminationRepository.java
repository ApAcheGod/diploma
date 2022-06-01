package com.example.diplom.repositories;

import com.example.diplom.entities.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExaminationRepository extends
        PagingAndSortingRepository<Examination, UUID>,
        JpaSpecificationExecutor<Examination>,
        JpaRepository<Examination, UUID> {

}
