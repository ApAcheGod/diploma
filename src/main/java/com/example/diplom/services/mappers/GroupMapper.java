package com.example.diplom.services.mappers;

import com.example.diplom.entities.Group;
import com.example.diplom.entities.Subject;
import com.example.diplom.entities.dto.GroupDto;
import com.example.diplom.entities.dto.to.Student2Dto;
import com.example.diplom.services.*;
import com.example.diplom.services.mappers.mappers2.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final ModelMapper modelMapper;
    private final Student2Mapper student2Mapper;
//    private final Room2Mapper room2Mapper;
    private final Subject2Mapper subject2Mapper;
    private final Task2Mapper task2Mapper;
    private final Teacher2Mapper teacher2Mapper;

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final TaskService taskService;
    private final Room2Mapper room2Mapper;
    private final TeacherService teacherService;
    private final RoomService roomService;


    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Group.class, GroupDto.class)
                .addMappings(m -> m.skip(GroupDto::setStudents))
                .addMappings(m -> m.skip(GroupDto::setRooms))
                .addMappings(m -> m.skip(GroupDto::setSubjects))
                .addMappings(m -> m.skip(GroupDto::setTasks))
                .addMappings(m -> m.skip(GroupDto::setCountOfStudents))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(GroupDto.class, Group.class)
                .addMappings(m -> m.skip(Group::setRooms))
                .addMappings(m -> m.skip(Group::setStudents))
                .addMappings(m -> m.skip(Group::addSubjects))
//                .addMappings(m -> m.skip(Group::setSubjects))
                .setPostConverter(toEntityConverter());
    }


    private Converter<GroupDto, Group> toEntityConverter(){
        return mappingContext -> {
            GroupDto source = mappingContext.getSource();
            Group destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Group, GroupDto> toDtoConverter() {
        return mappingContext -> {
            Group source = mappingContext.getSource();
            GroupDto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Group source, GroupDto destination) {

        if (source.getRooms() != null){
            destination.setRooms(source.getRooms().stream().map(room2Mapper::toDto).collect(Collectors.toSet()));
        }

        if (source.getTasks() != null){
            destination.setTasks(source.getTasks().stream().map(task2Mapper::toDto).collect(Collectors.toSet()));
        }

        if (source.getSubjects() != null){
            destination.setSubjects(source.getSubjects().stream().map(subject2Mapper::toDto).collect(Collectors.toSet()));
        }

        if (source.getStudents() != null){
            Set<Student2Dto> student2Dtos = source.getStudents().stream().map(student2Mapper::toDto).collect(Collectors.toSet());
            destination.setStudents(student2Dtos);
            destination.setCountOfStudents(student2Dtos.size());
        }
    }

    private void mapSpecificFields(GroupDto source, Group destination) {

        if (source.getStudents() != null){
            source.getStudents().forEach(student2Dto -> destination.addStudents(studentService.findById(student2Dto.getId())));
        }

        if (source.getRooms() != null){
            source.getRooms().forEach(room2Dto -> destination.addRooms(roomService.findById(room2Dto.getId())));
        }

        if (source.getSubjects() != null){
            Set<Subject> subjects = new HashSet<>();
            source.getSubjects().forEach(subject2Dto -> subjects.add(subjectService.findById(subject2Dto.getId())));

//            destination.addSubjects(subjects);
            destination.setSubjects(subjects);
        }

    }

    public Group toEntity(GroupDto groupDto){
        return Objects.isNull(groupDto) ? null : modelMapper.map(groupDto, Group.class);
    }

    public GroupDto toDto(Group group){
        return Objects.isNull(group) ? null : modelMapper.map(group, GroupDto.class);
    }
}
