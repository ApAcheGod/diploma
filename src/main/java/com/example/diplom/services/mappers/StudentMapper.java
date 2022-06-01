package com.example.diplom.services.mappers;

import com.example.diplom.entities.Student;
import com.example.diplom.entities.dto.StudentDto;
import com.example.diplom.services.GroupService;
import com.example.diplom.services.SolutionService;
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

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Student.class, StudentDto.class)
                .addMappings(m -> m.skip(StudentDto::setGroupId))
                .addMappings(m -> m.skip(StudentDto::setSolutions))
                .addMappings(m -> m.skip(StudentDto::setTasks))
                .addMappings(m -> m.skip(StudentDto::setName))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(StudentDto.class, Student.class)
                .addMappings(m -> m.skip(Student::setGroup))
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

        destination.setName(source.getName());

        if (source.getGroup() != null){
            destination.setGroupId(source.getGroup().getId());
        }

        if (source.getSolutions() != null){
            destination.setSolutions(source.getSolutions().stream().map(solutionMapper::toDto).collect(Collectors.toSet()));
        }

        if (source.getTasks() != null && source.getGroup() != null){
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
    }

    public Student toEntity(StudentDto studentDto){
        return Objects.isNull(studentDto) ? null : modelMapper.map(studentDto, Student.class);
    }

    public StudentDto toDto(Student student){
        StudentDto studentDto = null;
        if (!Objects.isNull(student)){
            studentDto = modelMapper.map(student, StudentDto.class);
            for (var task: studentDto.getTasks()){
                for (var solution: studentDto.getSolutions()){
                    if (task.getId().equals(solution.getTaskId())){
                        task.setHaveSolution(true);
                        break;
                    }
                    task.setHaveSolution(false);
                }
                for (var solution: studentDto.getSolutions()){
                    if (solution.getExamination() != null){
                        if (solution.getExamination().getTaskId().equals(task.getId())){
                            task.setHaveExamination(true);
                            break;
                        }
                    }
                    task.setHaveExamination(false);
                }
            }
        }
        return studentDto;
    }
}
