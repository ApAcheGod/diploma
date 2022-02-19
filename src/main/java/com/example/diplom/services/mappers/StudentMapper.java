package com.example.diplom.services.mappers;

import com.example.diplom.entities.Student;
import com.example.diplom.entities.dto.StudentDto;
import com.example.diplom.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StudentMapper {

    private final ModelMapper modelMapper;
    private final GroupService groupService;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Student.class, StudentDto.class)
                .addMappings(m -> m.skip(StudentDto::setGroupId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(StudentDto.class, Student.class)
                .addMappings(m -> m.skip(Student::setGroup)).setPostConverter(toEntityConverter());
    }

    private Converter<StudentDto, Student> toEntityConverter(){
        return mappingContext -> {
            StudentDto source = mappingContext.getSource();
            Student destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Student, StudentDto> toDtoConverter() {
        return mappingContext -> {
            Student source = mappingContext.getSource();
            StudentDto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Student source, StudentDto destination) {
        if (source.getGroup() != null && source.getGroup().getId() != null){
            destination.setGroupId(source.getGroup().getId());
        }
    }

    private void mapSpecificFields(StudentDto source, Student destination) {
        if (source.getGroupId() != null){
            destination.addGroup(groupService.findById(source.getGroupId()).get());
        }

    }

    public Student toEntity(StudentDto studentDto){
        return Objects.isNull(studentDto) ? null : modelMapper.map(studentDto, Student.class);
    }

    public StudentDto toDto(Student student){
        return Objects.isNull(student) ? null : modelMapper.map(student, StudentDto.class);
    }
}
