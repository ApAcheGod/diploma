//package com.example.diplom.repositories.Impl;
//
//import com.example.diplom.entities.Journal;
//import com.example.diplom.repositories.JournalRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class JournalRepositoryImpl implements JournalRepository {
//
//    private final EntityManager entityManager;
//
//
//    @Override
//    public List findAllBy() {
//        return entityManager.createNativeQuery("""
//                select
//                       examinations.id                          as examination_id,
//                       examination_status                       as examination_status,
//                       examinations.mark                        as examination_mark,
//                       diploma.teachers.id                      as teacher_id,
//                       diploma.users.login                      as teacher_login,
//                       diploma.subjects.id                      as subject_id,
//                       diploma.subjects.subject_name            as subject_name,
//                       diploma.groups.id                        as group_id,
//                       diploma.groups.group_name                as group_name,
//                       diploma.students.id                      as student_id,
//                       s.login                                  as student_login,
//                       diploma.tasks.id                         as task_id,
//                       diploma.tasks.task_name                  as task_name,
//                       diploma.solutions.id                     as solution_id,
//                       diploma.solutions.date_of_delivery       as date_of_delivery,
//                       diploma.examinations.date_of_valuation   as date_of_valuation
//                from diploma.examinations
//
//                left join diploma.solutions on examinations.solution_id = solutions.id
//                left join diploma.tasks on solutions.task_id = tasks.id
//                left join diploma.students on solutions.student_id = students.id
//                left join diploma.groups on students.group_id = groups.id
//                left join diploma.subjects on tasks.subject_id = subjects.id
//                left join diploma.teachers on tasks.teacher_id = teachers.id
//                left join diploma.users on teachers.id = diploma.users.id
//                left join (select id, login from diploma.users) s on s.id = solutions.student_id
//""", Journal.class).getResultList();
//    }
//}
