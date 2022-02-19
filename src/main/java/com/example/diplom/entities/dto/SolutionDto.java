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
public class SolutionDto {

    private UUID id;
    private String text;
//    private StudentDto student;
    private UUID studentId;
//    private TaskDto task;
    private UUID taskId;

}
