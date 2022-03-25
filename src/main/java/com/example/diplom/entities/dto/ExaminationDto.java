package com.example.diplom.entities.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExaminationDto {

    private UUID examinationId;

    private String examinationStatus;

    private int mark;

    private UUID teacherId;

    private String teacherLogin;

    private UUID subjectId;

    private String subjectName;

    private UUID groupId;

    private String groupName;

    private UUID studentId;

    private String studentLogin;

    private UUID taskId;

    private String taskName;

    private UUID solutionId;

    private LocalDateTime dateOfDelivery;

    private LocalDateTime dateOfValuation;
}
