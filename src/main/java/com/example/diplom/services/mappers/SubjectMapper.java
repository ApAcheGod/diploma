package com.example.diplom.services.mappers;

import com.example.diplom.entities.Subject;
import com.example.diplom.entities.dto.SubjectDto;
import com.example.diplom.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SubjectMapper {

    private final ModelMapper modelMapper;

    private final RoomService roomService;
    private final TeacherService teacherService;
    private final GroupService groupService;
    private final MaterialService materialService;
    private final TaskService taskService;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Subject.class, SubjectDto.class)
                .addMappings(m -> m.skip(SubjectDto::setRoomId))
                .addMappings(m -> m.skip(SubjectDto::setTeacherId))
                .addMappings(m -> m.skip(SubjectDto::setTeacherName))
                .addMappings(m -> m.skip(SubjectDto::setRoomName))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(SubjectDto.class, Subject.class)
                .addMappings(m -> m.skip(Subject::setRoom))
                .addMappings(m -> m.skip(Subject::setTeacher))
                .setPostConverter(toEntityConverter());
    }

    private Converter<SubjectDto, Subject> toEntityConverter(){
        return mappingContext -> {
            SubjectDto source = mappingContext.getSource();
            Subject destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Subject, SubjectDto> toDtoConverter() {
        return mappingContext -> {
            Subject source = mappingContext.getSource();
            SubjectDto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Subject source, SubjectDto destination) {

        destination.setTeacherName(source.getTeacher().getLast_name()
                + " " + source.getTeacher().getFirst_name()
                + " " + source.getTeacher().getPatronymic());

        if (source.getRoom() != null){
            destination.setRoomName(source.getRoom().getName());
        }

        if (source.getRoom() != null){
            destination.setRoomId(source.getRoom().getId());
        }

        if (source.getTeacher() != null){
            destination.setTeacherId(source.getTeacher().getId());
        }
    }

    private void mapSpecificFields(SubjectDto source, Subject destination) {

        if (source.getRoomId() != null){
            destination.addRoom(roomService.findById(source.getRoomId()));
        }

        if (source.getTeacherId() != null){
            destination.addTeacher(teacherService.findById(source.getTeacherId()));
        }

        if (source.getGroups() != null){
            source.getGroups().forEach(group2Dto ->  destination.setGroups(groupService.findById(group2Dto.getId())));
        }

        if (source.getMaterials() != null){
            source.getMaterials().forEach(materialDto ->  destination.addMaterials(materialService.findById(materialDto.getId())));
        }

        if (source.getTasks() != null){
            source.getTasks().forEach(task2Dto ->  destination.addTasks(taskService.findById(task2Dto.getId())));
        }

    }

    public Subject toEntity(SubjectDto subjectDto){
        return Objects.isNull(subjectDto) ? null : modelMapper.map(subjectDto, Subject.class);
    }

    public SubjectDto toDto(Subject subject){
        return Objects.isNull(subject) ? null : modelMapper.map(subject, SubjectDto.class);
    }
}
