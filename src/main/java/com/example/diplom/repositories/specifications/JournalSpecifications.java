package com.example.diplom.repositories.specifications;

import com.example.diplom.entities.Journal;
import org.springframework.data.jpa.domain.Specification;

public class JournalSpecifications {

    public static Specification<Journal> withExaminationStatus(String examinationStatus){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("examinationStatus"), examinationStatus);
    }

    public static Specification<Journal> withTaskName(String taskName){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("taskName")), "%" + taskName.toLowerCase() + "%");
    }

    public static Specification<Journal> withSubjectName(String subjectName){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("subjectName")), "%" + subjectName.toLowerCase() + "%");
    }

    public static Specification<Journal> withTeacherLogin(String teacherLogin){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("teacherLogin")), "%" + teacherLogin.toLowerCase() + "%");
    }

    public static Specification<Journal> withStudentLogin(String studentLogin){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("studentLogin")), "%" + studentLogin.toLowerCase() + "%");
    }


}
