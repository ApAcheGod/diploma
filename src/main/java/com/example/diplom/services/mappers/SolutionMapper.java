package com.example.diplom.services.mappers;

import com.example.diplom.entities.Solution;
import com.example.diplom.entities.dto.SolutionDto;
import com.example.diplom.services.StudentService;
import com.example.diplom.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SolutionMapper {

    private final ModelMapper modelMapper;

    private final StudentService studentService;
    private final TaskService taskService;
    private final ExaminationMapper examinationMapper;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Solution.class, SolutionDto.class)
                .addMappings(m -> m.skip(SolutionDto::setStudentId))
                .addMappings(m -> m.skip(SolutionDto::setTaskId))
                .addMappings(m -> m.skip(SolutionDto::setStudentName))
                .addMappings(m -> m.skip(SolutionDto::setTaskName))
                .addMappings(m -> m.skip(SolutionDto::setExamination))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(SolutionDto.class, Solution.class)
                .addMappings(m -> m.skip(Solution::setStudent))
                .addMappings(m -> m.skip(Solution::setTask))
                .setPostConverter(toEntityConverter());
    }

    private Converter<SolutionDto, Solution> toEntityConverter(){
        return mappingContext -> {
            SolutionDto source = mappingContext.getSource();
            Solution destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Solution, SolutionDto> toDtoConverter() {
        return mappingContext -> {
            Solution source = mappingContext.getSource();
            SolutionDto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Solution source, SolutionDto destination) {

        if (source.getExamination() != null){
            destination.setExamination(examinationMapper.toDto(source.getExamination()));
        }

        if (source.getStudent() != null){
            destination.setStudentName(source.getStudent().getName());
            destination.setStudentId(source.getStudent().getId());
        }

        if (source.getTask() != null){
            destination.setTaskId(source.getTask().getId());
            destination.setTaskName(source.getTask().getName());
        }
    }

    private void mapSpecificFields(SolutionDto source, Solution destination) {

        if (source.getStudentId() != null){
            destination.addStudent(studentService.findById(source.getStudentId()));
        }

        if (source.getTaskId() != null){
            destination.addTask(taskService.findById(source.getTaskId()));
        }
    }

    public Solution toEntity(SolutionDto solutionDto){
        return Objects.isNull(solutionDto) ? null : modelMapper.map(solutionDto, Solution.class);
    }

    public SolutionDto toDto(Solution solution){
        return Objects.isNull(solution) ? null : modelMapper.map(solution, SolutionDto.class);
    }
}
