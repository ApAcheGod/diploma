package com.example.diplom.entities.dto;

import com.example.diplom.entities.dto.to.Room2Dto;
import com.example.diplom.entities.dto.to.Student2Dto;
import com.example.diplom.entities.dto.to.Subject2Dto;
import com.example.diplom.entities.dto.to.Task2Dto;
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
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", scope = GroupDto.class)
public class GroupDto {

    private UUID id;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Student2Dto> students = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Subject2Dto> subjects = new HashSet<>();

//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private Set<Teacher2Dto> teachers = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Room2Dto> rooms = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Task2Dto> tasks = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupDto groupDto = (GroupDto) o;

        if (id != null ? !id.equals(groupDto.id) : groupDto.id != null) return false;
        if (name != null ? !name.equals(groupDto.name) : groupDto.name != null) return false;
        if (students != null ? !students.equals(groupDto.students) : groupDto.students != null) return false;
        if (rooms != null ? !rooms.equals(groupDto.rooms) : groupDto.rooms != null) return false;
        return tasks != null ? tasks.equals(groupDto.tasks) : groupDto.tasks == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (students != null ? students.hashCode() : 0);
        result = 31 * result + (rooms != null ? rooms.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }
}
