package com.example.diplom.services.mappers;

import com.example.diplom.entities.Student;
import com.example.diplom.entities.dto.StudentDto;
import com.example.diplom.services.GroupService;
import com.example.diplom.services.SolutionService;
import com.example.diplom.services.TaskService;
import com.example.diplom.services.mappers.mappers2.Task2Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentMapper {

    private final ModelMapper modelMapper;
    private final GroupService groupService;
    private final SolutionMapper solutionMapper;
    private final SolutionService solutionService;
    private final Task2Mapper task2Mapper;
    private final TaskService taskService;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Student.class, StudentDto.class)
                .addMappings(m -> m.skip(StudentDto::setGroupId))
                .addMappings(m -> m.skip(StudentDto::setSolutions))
                .addMappings(m -> m.skip(StudentDto::setTasks))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(StudentDto.class, Student.class)
                .addMappings(m -> m.skip(Student::setGroup))
                .addMappings(m -> m.skip(Student::setTasks))
                .addMappings(m -> m.skip(Student::setSolutions))
                .addMappings(m -> m.skip(Student::setLogin))
                .setPostConverter(toEntityConverter());
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

        if (source.getGroup() != null){
            destination.setGroupId(source.getGroup().getId());
        }

        if (source.getSolutions() != null){
            destination.setSolutions(source.getSolutions().stream().map(solutionMapper::toDto).collect(Collectors.toSet()));
        }

        if (source.getTasks() != null){
            destination.setTasks(source.getTasks().stream().map(task2Mapper::toDto).collect(Collectors.toSet()));
        }
    }

    private void mapSpecificFields(StudentDto source, Student destination) {
        if (source.getGroupId() != null){
            destination.addGroup(groupService.findById(source.getGroupId()));
        }

        if (source.getSolutions() != null){
            source.getSolutions().forEach(solutionDto -> destination.addSolution(solutionService.findById(solutionDto.getId())));
        }

        if (source.getTasks() != null){
            source.getTasks().forEach(task2Dto -> destination.addTasks(taskService.findById(task2Dto.getId())));
        }
    }

    public Student toEntity(StudentDto studentDto){
        return Objects.isNull(studentDto) ? null : modelMapper.map(studentDto, Student.class);
    }

    public StudentDto toDto(Student student){
        return Objects.isNull(student) ? null : modelMapper.map(student, StudentDto.class);
    }
}
