package com.example.diplom.services.mappers.mappers2;

import com.example.diplom.entities.Task;
import com.example.diplom.entities.dto.to.Task2Dto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Task2Mapper {

    private final ModelMapper modelMapper;


    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Task.class, Task2Dto.class)
                .addMappings(m -> m.skip(Task2Dto::setSubjectId))
                .addMappings(m -> m.skip(Task2Dto::setSubjectName))
                .addMappings(m -> m.skip(Task2Dto::setTeacherId))
                .addMappings(m -> m.skip(Task2Dto::setTeacherName))
                .addMappings(m -> m.skip(Task2Dto::setHaveSolution))
                .addMappings(m -> m.skip(Task2Dto::setHaveExamination))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(Task2Dto.class, Task.class)
                .addMappings(m -> m.skip(Task::setSubject))
                .addMappings(m -> m.skip(Task::setTeacher))
                .setPostConverter(toEntityConverter());
    }

    private Converter<Task2Dto, Task> toEntityConverter(){
        return mappingContext -> {
            Task2Dto source = mappingContext.getSource();
            Task destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Task, Task2Dto> toDtoConverter() {
        return mappingContext -> {
            Task source = mappingContext.getSource();
            Task2Dto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Task source, Task2Dto destination) {

        if (source.getSubject() != null){
            destination.setSubjectName(source.getSubject().getName());
            destination.setSubjectId(source.getSubject().getId());
            destination.setSubjectName(source.getSubject().getName());
        }

        if (source.getTeacher() != null){
            destination.setTeacherName(source.getTeacher().getTeacherName());
            destination.setTeacherId(source.getTeacher().getId());
            destination.setTeacherName(source.getTeacher().getTeacherName());
        }

    }

    private void mapSpecificFields(Task2Dto source, Task destination) {

//        if (source.getSubjectId() != null){
//            destination.addSubjects(subjectService.findById(source.getSubjectId()));
//        }
//
//        if (source.getTeacherId() != null){
//            destination.addTeacher(teacherService.findById(source.getTeacherId()));
//        }

//        if (source.getGroups() != null){
//            source.getGroups().forEach(g -> destination.addGroups(groupService.findById(g.getId())));
//        }
    }


    public Task toEntity(Task2Dto task2Dto){
        return Objects.isNull(task2Dto) ? null : modelMapper.map(task2Dto, Task.class);
    }

    public Task2Dto toDto(Task task){
        return Objects.isNull(task) ? null : modelMapper.map(task, Task2Dto.class);
    }
}
