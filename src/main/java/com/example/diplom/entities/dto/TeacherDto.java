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
public class TeacherDto {

    private UUID id;
    private String first_name;
    private String last_name;
    private String patronymic;
    private String login;
    private String email;
    private Set<SubjectDto> subjects = new HashSet<>();
    private Set<RoomDto> rooms = new HashSet<>();
    private Set<MaterialDto> materials = new HashSet<>();
    private Set<GroupDto> groups = new HashSet<>();
    private Set<TaskDto> tasks = new HashSet<>();

}
