package com.example.diplom.services.mappers.mappers2;

import com.example.diplom.entities.Room;
import com.example.diplom.entities.dto.to.Room2Dto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Room2Mapper {

    private final ModelMapper modelMapper;
    private final Group2Mapper group2Mapper;
    private final Subject2Mapper subject2Mapper;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Room.class, Room2Dto.class)
                .addMappings(m -> m.skip(Room2Dto::setTeacherId))
                .addMappings(m -> m.skip(Room2Dto::setGroups))
                .addMappings(m -> m.skip(Room2Dto::setSubjects))
                .addMappings(m -> m.skip(Room2Dto::setTeacherName))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(Room2Dto.class, Room.class)
                .addMappings(m -> m.skip(Room::setSubjects))
                .addMappings(m -> m.skip(Room::setTeacher))
                .addMappings(m -> m.skip(Room::setGroups))
                .setPostConverter(toEntityConverter());
    }

    private Converter<Room2Dto, Room> toEntityConverter(){
        return mappingContext -> {
            Room2Dto source = mappingContext.getSource();
            Room destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Room, Room2Dto> toDtoConverter() {
        return mappingContext -> {
            Room source = mappingContext.getSource();
            Room2Dto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Room source, Room2Dto destination) {

        if (source.getGroups() != null){
            destination.setGroups(source.getGroups().stream().map(group2Mapper::toDto).collect(Collectors.toSet()));
        }

        if (source.getSubjects() != null){
            destination.setSubjects(source.getSubjects().stream().map(subject2Mapper::toDto).collect(Collectors.toSet()));
        }

        if (source.getTeacher() != null){
            destination.setTeacherName(source.getTeacher().getTeacherName());
            destination.setTeacherId(source.getTeacher().getId());
            destination.setTeacherName(source.getTeacher().getTeacherName());
        }

//        if (source.getGroups() != null){
//            destination.setGroups(source.getGroups().stream().map(groupMapper::toDto).collect(Collectors.toSet()));
//        }
    }

    private void mapSpecificFields(Room2Dto source, Room destination) {

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

    public Room toEntity(Room2Dto room2Dto){
        return Objects.isNull(room2Dto) ? null : modelMapper.map(room2Dto, Room.class);
    }

    public Room2Dto toDto(Room room){
        return Objects.isNull(room) ? null : modelMapper.map(room, Room2Dto.class);
    }

}
