package com.example.diplom.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private String first_name;
    private String id;
    private String last_name;
    private String patronymic;
    private String login;
    private String email;
    private UUID groupId;
    private Set<SolutionDto> solutions = new HashSet<>();
    private Set<TaskDto> tasks = new HashSet<>();

}
