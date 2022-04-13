package com.example.diplom.entities.dto.to;

import com.example.diplom.entities.dto.MaterialDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"subjects"})
public class Room2Dto {

    private UUID id;

    private String name;

    private UUID teacherId;

    private String teacherName;

//    private Set<Group2Dto> groups = new HashSet<>();

    private Set<Subject2Dto> subjects = new HashSet<>();

    private Set<MaterialDto> materials = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room2Dto room2Dto = (Room2Dto) o;

        if (id != null ? !id.equals(room2Dto.id) : room2Dto.id != null) return false;
        if (name != null ? !name.equals(room2Dto.name) : room2Dto.name != null) return false;
        if (teacherId != null ? !teacherId.equals(room2Dto.teacherId) : room2Dto.teacherId != null) return false;
        if (teacherName != null ? !teacherName.equals(room2Dto.teacherName) : room2Dto.teacherName != null)
            return false;
        return materials != null ? materials.equals(room2Dto.materials) : room2Dto.materials == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (teacherId != null ? teacherId.hashCode() : 0);
        result = 31 * result + (teacherName != null ? teacherName.hashCode() : 0);
        result = 31 * result + (materials != null ? materials.hashCode() : 0);
        return result;
    }
}
