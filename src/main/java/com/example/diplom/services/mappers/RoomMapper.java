package com.example.diplom.services.mappers;

import com.example.diplom.entities.Room;
import com.example.diplom.entities.dto.RoomDto;
import com.example.diplom.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    private final ModelMapper modelMapper;
    private final TeacherService teacherService;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Room.class, RoomDto.class)
                .addMappings(m -> m.skip(RoomDto::setTeacherId))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(RoomDto.class, Room.class)
                .addMappings(m -> m.skip(Room::setTeacher))
                .setPostConverter(toEntityConverter());
    }

    private Converter<RoomDto, Room> toEntityConverter(){
        return mappingContext -> {
            RoomDto source = mappingContext.getSource();
            Room destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Room, RoomDto> toDtoConverter() {
        return mappingContext -> {
            Room source = mappingContext.getSource();
            RoomDto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Room source, RoomDto destination) {

        if (source.getTeacher() != null){
            destination.setTeacherId(source.getTeacher().getId());
        }
    }

    private void mapSpecificFields(RoomDto source, Room destination) {

        if (source.getTeacherId() != null){
            destination.addTeacher(teacherService.findById(source.getTeacherId()));
        }
    }

    public Room toEntity(RoomDto roomDto){
        return Objects.isNull(roomDto) ? null : modelMapper.map(roomDto, Room.class);
    }

    public RoomDto toDto(Room room){
        return Objects.isNull(room) ? null : modelMapper.map(room, RoomDto.class);
    }
}
