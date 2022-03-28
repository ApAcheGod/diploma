package com.example.diplom.entities.dto.to;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", scope = Room2Dto.class)
public class Room2Dto {

    private UUID id;

    private String name;

    private UUID teacherId;

    private String teacherName;

    private Set<Group2Dto> groups = new HashSet<>();

    private Set<Subject2Dto> subjects = new HashSet<>();

}
