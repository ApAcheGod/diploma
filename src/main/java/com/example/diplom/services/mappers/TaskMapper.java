package com.example.diplom.services.mappers;

import com.example.diplom.entities.Task;
import com.example.diplom.entities.dto.TaskDto;
import com.example.diplom.services.SubjectService;
import com.example.diplom.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final ModelMapper modelMapper;

    private final SubjectService subjectService;
    private final TeacherService teacherService;
//    private final GroupMapper groupMapper;
//    private final GroupService groupService;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Task.class, TaskDto.class)
                .addMappings(m -> m.skip(TaskDto::setSubjectId))
                .addMappings(m -> m.skip(TaskDto::setTeacherId))
                .addMappings(m -> m.skip(TaskDto::setGroups))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TaskDto.class, Task.class)
                .addMappings(m -> m.skip(Task::setSubject))
                .addMappings(m -> m.skip(Task::setTeacher))
                .addMappings(m -> m.skip(Task::setGroups))
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
            destination.setSubjectId(source.getSubject().getId());
        }

        if (source.getTeacher() != null){
            destination.setTeacherId(source.getTeacher().getId());
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
//            source.getGroups().forEach(g -> destination.addGroups(groupService.findById(g.getId())));
//        }
    }

    public Task toEntity(TaskDto taskDto){
        return Objects.isNull(taskDto) ? null : modelMapper.map(taskDto, Task.class);
    }

    public TaskDto toDto(Task task){
        return Objects.isNull(task) ? null : modelMapper.map(task, TaskDto.class);
    }
}
