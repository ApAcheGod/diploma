package com.example.diplom.entities.dto.to;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Task2Dto {

    private UUID id;

    private String name;

    private UUID subjectId;

    private String subjectName;

    private UUID teacherId;

    private String teacherName;

    private LocalDateTime last_date_of_delivery;

    private String text;

    private Boolean haveSolution = false;

    private Boolean haveExamination = false;
}
