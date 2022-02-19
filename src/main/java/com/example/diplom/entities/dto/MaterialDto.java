package com.example.diplom.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDto {

    private UUID id;
    private String name;
//    private TeacherDto teacher;
//    private SubjectDto subject;
    private UUID teacherId;
    private UUID subjectId;
    private String text;

}
