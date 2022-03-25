//package com.example.diplom.entities;
//
//
//import lombok.*;
//import org.hibernate.annotations.Immutable;
//import org.hibernate.annotations.Type;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Entity
//@Immutable
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@RequiredArgsConstructor
//@Builder
//public class Journal {
//
//    @Id
//    @Type(type="pg-uuid")
//    @Column(name = "examination_id")
//    private UUID examinationId;
//
//    @Column(name = "examination_status")
//    private String examinationStatus;
//
//    @Column(name = "examination_mark")
//    private Integer mark;
//
//    @Type(type="pg-uuid")
//    @Column(name = "teacher_id")
//    private UUID teacherId;
//
//    @Column(name = "teacher_login")
//    private String teacherLogin;
//
//    @Type(type="pg-uuid")
//    @Column(name = "subject_id")
//    private UUID subjectId;
//
//    @Column(name = "subject_name")
//    private String subjectName;
//
//    @Type(type="pg-uuid")
//    @Column(name = "group_id")
//    private UUID groupId;
//
//    @Column(name = "group_name")
//    private String groupName;
//
//    @Type(type="pg-uuid")
//    @Column(name = "student_id")
//    private UUID studentId;
//
//    @Column(name = "student_login")
//    private String studentLogin;
//
//    @Type(type="pg-uuid")
//    @Column(name = "task_id")
//    private UUID taskId;
//
//    @Column(name = "task_name")
//    private String taskName;
//
//    @Type(type="pg-uuid")
//    @Column(name = "solution_id")
//    private UUID solutionId;
//
//    @Column(name = "date_of_delivery")
//    private LocalDate dateOfDelivery;
//
//    @Column(name = "date_of_valuation")
//    private LocalDate dateOfValuation;
//}
