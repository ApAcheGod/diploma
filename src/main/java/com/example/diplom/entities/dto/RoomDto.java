package com.example.diplom.entities.dto;

import com.example.diplom.entities.dto.to.Subject2Dto;
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
public class RoomDto {

    private UUID id;

    private String name;

    private UUID teacherId;

    private String teacherName;

//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private Set<Group2Dto> groups = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Subject2Dto> subjects = new HashSet<>();
}
