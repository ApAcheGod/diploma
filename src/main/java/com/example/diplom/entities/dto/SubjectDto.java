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
public class SubjectDto {

    private UUID id;
    private String name;
//    private TeacherDto teacher;
    private UUID teacherId;
    private Set<MaterialDto> materials = new HashSet<>();
    private Set<TaskDto> tasks = new HashSet<>();
    private Set<GroupDto> groups = new HashSet<>();
//    private RoomDto room;
    private UUID roomId;
}