package com.example.diplom.services.mappers;

import com.example.diplom.entities.Teacher;
import com.example.diplom.entities.dto.TeacherDto;
import com.example.diplom.services.MaterialService;
import com.example.diplom.services.RoomService;
import com.example.diplom.services.SubjectService;
import com.example.diplom.services.TaskService;
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
    private final Room2Mapper room2Mapper;
    private final Subject2Mapper subject2Mapper;
    private final Task2Mapper task2Mapper;
    private final RoomService roomService;
    private final SubjectService subjectService;
    private final MaterialService materialService;
    private final TaskService taskService;
    private final MaterialMapper materialMapper;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Teacher.class, TeacherDto.class)
                .addMappings(m -> m.skip(TeacherDto::setRooms))
                .addMappings(m -> m.skip(TeacherDto::setSubjects))
                .addMappings(m -> m.skip(TeacherDto::setTeacherName))
                .addMappings(m -> m.skip(TeacherDto::setTasks))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TeacherDto.class, Teacher.class)
                .addMappings(m -> m.skip(Teacher::setRooms))
                .addMappings(m -> m.skip(Teacher::setSubjects))
                .addMappings(m -> m.skip(Teacher::setLogin))
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

        destination.setTeacherName(source.getTeacherName());

        if (source.getRooms() != null){
            destination.setRooms(source.getRooms().stream().map(room2Mapper::toDto).collect(Collectors.toSet()));
        }

        if (source.getSubjects() != null){
            destination.setSubjects(source.getSubjects().stream().map(subject2Mapper::toDto).collect(Collectors.toSet()));
        }

        if (source.getTasks() != null){
            destination.setTasks(source.getTasks().stream().map(task2Mapper::toDto).collect(Collectors.toSet()));
        }

        if (source.getMaterials() != null){
            destination.setMaterials(source.getMaterials().stream().map(materialMapper::toDto).collect(Collectors.toSet()));
        }
    }

    private void mapSpecificFields(TeacherDto source, Teacher destination) {

        if (source.getRooms() != null){
            source.getRooms().forEach(room2Dto ->  destination.addRooms(roomService.findById(room2Dto.getId())));
        }

        if (source.getSubjects() != null){
            source.getSubjects().forEach(subject2Dto ->  destination.addSubjects(subjectService.findById(subject2Dto.getId())));
        }

        if (source.getMaterials() != null){
            source.getMaterials().forEach(materialDto ->  destination.addMaterials(materialService.findById(materialDto.getId())));
        }

        if (source.getTasks() != null){
            source.getTasks().forEach(task2Dto ->  destination.addTasks(taskService.findById(task2Dto.getId())));
        }
    }


    public Teacher toEntity(TeacherDto teacherDto){
        return Objects.isNull(teacherDto) ? null : modelMapper.map(teacherDto, Teacher.class);
    }

    public TeacherDto toDto(Teacher teacher){
        return Objects.isNull(teacher) ? null : modelMapper.map(teacher, TeacherDto.class);
    }
}
