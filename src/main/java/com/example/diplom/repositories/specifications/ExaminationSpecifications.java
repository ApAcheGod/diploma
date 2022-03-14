package com.example.diplom.repositories.specifications;

import com.example.diplom.entities.Examination;
import org.springframework.data.jpa.domain.Specification;

public class ExaminationSpecifications {

    public static Specification<Examination> withTaskName(String taskName){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("taskName"), taskName);
    }

    public static Specification<Examination> withSubjectName(String subjectName){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("subjectName"), subjectName);
    }
}