package com.example.diplom.services.mappers;

import com.example.diplom.entities.Task;
import com.example.diplom.entities.dto.TaskDto;
import com.example.diplom.services.GroupService;
import com.example.diplom.services.SolutionService;
import com.example.diplom.services.SubjectService;
import com.example.diplom.services.TeacherService;
import com.example.diplom.services.mappers.mappers2.Group2Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final ModelMapper modelMapper;

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final SolutionMapper solutionMapper;
    private final SolutionService solutionService;
    private final Group2Mapper groupMapper;
    private final GroupService groupService;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Task.class, TaskDto.class)
                .addMappings(m -> m.skip(TaskDto::setSubjectId))
                .addMappings(m -> m.skip(TaskDto::setSubjectName))
                .addMappings(m -> m.skip(TaskDto::setTeacherId))
                .addMappings(m -> m.skip(TaskDto::setTeacherName))
//                .addMappings(m -> m.skip(TaskDto::setGroups))
                .addMappings(m -> m.skip(TaskDto::setSolutions))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TaskDto.class, Task.class)
                .addMappings(m -> m.skip(Task::setSubject))
                .addMappings(m -> m.skip(Task::setTeacher))
//                .addMappings(m -> m.skip(Task::setGroups))
                .addMappings(m -> m.skip(Task::setSolutions))
                .setPostConverter(toEntityConverter());
    }

    private Converter<TaskDto, Task> toEntityConverter(){
        return mappingContext -> {
            TaskDto source = mappingContext.getSource();
            Task destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Task, TaskDto> toDtoConverter() {
        return mappingContext -> {
            Task source = mappingContext.getSource();
            TaskDto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Task source, TaskDto destination) {

        if (source.getSubject() != null){
            destination.setSubjectName(source.getSubject().getName());
            destination.setSubjectId(source.getSubject().getId());
        }

        if (source.getTeacher() != null){
            destination.setTeacherName(source.getTeacher().getTeacherName());
            destination.setTeacherId(source.getTeacher().getId());
        }

        if (source.getSolutions() != null){
            destination.setSolutions(source.getSolutions().stream().map(solutionMapper::toDto).collect(Collectors.toSet()));
        }

//        if (source.getGroups() != null){
//            destination.setGroups(source.getGroups().stream().map(groupMapper::toDto).collect(Collectors.toSet()));
//        }
    }

    private void mapSpecificFields(TaskDto source, Task destination) {

        if (source.getSubjectId() != null){
            destination.addSubjects(subjectService.findById(source.getSubjectId()));
        }

        if (source.getTeacherId() != null){
            destination.addTeacher(teacherService.findById(source.getTeacherId()));
        }

//        if (source.getGroups() != null){
//            source.getGroups().forEach(group2Dto -> destination.addGroups(groupService.findById(group2Dto.getId())));
//        }

        if (source.getSolutions() != null){
            source.getSolutions().forEach(solutionDto -> destination.addSolutions(solutionService.findById(solutionDto.getId())));
        }
    }

    public Task toEntity(TaskDto taskDto){
        return Objects.isNull(taskDto) ? null : modelMapper.map(taskDto, Task.class);
    }

    public TaskDto toDto(Task task){
        return Objects.isNull(task) ? null : modelMapper.map(task, TaskDto.class);
    }
}
