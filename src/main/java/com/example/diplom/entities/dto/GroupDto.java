package com.example.diplom.entities.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
public class GroupDto {

    private UUID id;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<StudentDto> students = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<SubjectDto> subjects = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<TeacherDto> teachers = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<RoomDto> rooms = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<TaskDto> tasks = new HashSet<>();

}
