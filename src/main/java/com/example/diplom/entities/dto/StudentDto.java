package com.example.diplom.entities.dto;

import com.example.diplom.entities.dto.to.Task2Dto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class StudentDto {

    private String id;

    private String first_name;

    private String last_name;

    private String patronymic;

    private String login;

    private String email;

    private UUID groupId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<SolutionDto> solutions = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Task2Dto> tasks = new HashSet<>();

}
