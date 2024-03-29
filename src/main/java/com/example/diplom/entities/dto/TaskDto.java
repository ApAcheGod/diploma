package com.example.diplom.entities.dto;

import com.example.diplom.entities.dto.to.Group2Dto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class TaskDto {

    private UUID id;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Group2Dto> groups = new HashSet<>();

    private UUID subjectId;

    private String subjectName;

    private UUID teacherId;

    private String teacherName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date_of_creation;

    private String text;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<SolutionDto> solutions = new HashSet<>();

}
