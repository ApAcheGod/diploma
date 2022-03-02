package com.example.diplom.entities.dto;

import com.example.diplom.entities.dto.to.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

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
public class GroupDto {

    private UUID id;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Student2Dto> students = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Subject2Dto> subjects = new HashSet<>();

//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private Set<Teacher2Dto> teachers = new HashSet<>();

//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private Set<Room2Dto> rooms = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Task2Dto> tasks = new HashSet<>();

}
