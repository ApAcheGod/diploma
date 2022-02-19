package com.example.diplom.entities.dto;

import com.example.diplom.entities.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private UUID id;
    private String name;
    private Set<StudentDto> students = new HashSet<>();
    private Set<GroupDto> groups = new HashSet<>();
//    private SubjectDto subject;
//    private TeacherDto teacher;
    private UUID subjectId;
    private UUID teacherId;
    private LocalDateTime last_date_of_delivery;
    private int min_rating;
    private int max_rating;
    private Boolean isTemporal;
    private Boolean isMandatory; // обязательное задание
    private TaskType taskType;
    private int count_of_attempts; // количество сдачь
    private String text;
    private Set<SolutionDto> solutions = new HashSet<>();

}
