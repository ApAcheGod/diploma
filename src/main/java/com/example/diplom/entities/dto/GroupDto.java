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
public class GroupDto {

    private UUID id;
    private String name;
    private Set<StudentDto> students = new HashSet<>();
    private Set<SubjectDto> subjects = new HashSet<>();
    private Set<TeacherDto> teachers = new HashSet<>();
    private Set<RoomDto> rooms = new HashSet<>();
    private Set<TaskDto> tasks = new HashSet<>();

}
