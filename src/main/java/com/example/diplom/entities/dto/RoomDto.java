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
public class RoomDto {

    private UUID id;
    private String name;
    private UUID teacherId;
    private Set<GroupDto> groups = new HashSet<>();
    private Set<SubjectDto> subjects = new HashSet<>();
}
