package com.example.diplom.services.mappers;

import com.example.diplom.entities.Task;
import com.example.diplom.entities.Teacher;
import com.example.diplom.entities.dto.TaskDto;
import com.example.diplom.entities.dto.TeacherDto;
import com.example.diplom.entities.dto.to.Group2Dto;
import com.example.diplom.entities.dto.to.Room2Dto;
import com.example.diplom.entities.dto.to.Subject2Dto;
import com.example.diplom.entities.dto.to.Task2Dto;
import com.example.diplom.services.TeacherService;
import com.example.diplom.services.mappers.mappers2.Group2Mapper;
import com.example.diplom.services.mappers.mappers2.Room2Mapper;
import com.example.diplom.services.mappers.mappers2.Subject2Mapper;
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
public class TeacherMapper {

    private final ModelMapper modelMapper;
    private final TeacherService teacherService;
    private final Room2Mapper room2Mapper;
    private final Subject2Mapper subject2Mapper;
    private final Group2Mapper group2Mapper;
    private final Task2Mapper task2Mapper;
//    private final GroupMapper groupMapper;
//    private final GroupService groupService;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Teacher.class, TeacherDto.class)
                .addMappings(m -> m.skip(TeacherDto::setRooms))
                .addMappings(m -> m.skip(TeacherDto::setSubjects))
//                .addMappings(m -> m.skip(TeacherDto::setGroups))
                .addMappings(m -> m.skip(TeacherDto::setTasks))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TeacherDto.class, Teacher.class)
                .addMappings(m -> m.skip(Teacher::setRooms))
                .addMappings(m -> m.skip(Teacher::setSubjects))
//                .addMappings(m -> m.skip(Teacher::setGroups))
                .addMappings(m -> m.skip(Teacher::setTasks))
                .setPostConverter(toEntityConverter());
    }

    private Converter<TeacherDto, Teacher> toEntityConverter(){
        return mappingContext -> {
            TeacherDto source = mappingContext.getSource();
            Teacher destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Teacher, TeacherDto> toDtoConverter() {
        return mappingContext -> {
            Teacher source = mappingContext.getSource();
            TeacherDto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Teacher source, TeacherDto destination) {

        if (source.getRooms() != null){
            destination.setRooms(source.getRooms().stream().map(room2Mapper::toDto).collect(Collectors.toSet()));
        }
        if (source.getSubjects() != null){
            destination.setSubjects(source.getSubjects().stream().map(subject2Mapper::toDto).collect(Collectors.toSet()));
        }
//        if (source.getGroups() != null){
//            destination.setGroups(source.getGroups().stream().map(group2Mapper::toDto).collect(Collectors.toSet()));
//        }
        if (source.getTasks() != null){
            destination.setTasks(source.getTasks().stream().map(task2Mapper::toDto).collect(Collectors.toSet()));
        }
    }

    private void mapSpecificFields(TeacherDto source, Teacher destination) {

//        if (source.getRooms() != null){
//            destination.addSubjects(subjectService.findById(source.getSubjectId()));
//        }

    }


    public Teacher toEntity(TeacherDto teacherDto){
        return Objects.isNull(teacherDto) ? null : modelMapper.map(teacherDto, Teacher.class);
    }

    public TeacherDto toDto(Teacher teacher){
        return Objects.isNull(teacher) ? null : modelMapper.map(teacher, TeacherDto.class);
    }
}
