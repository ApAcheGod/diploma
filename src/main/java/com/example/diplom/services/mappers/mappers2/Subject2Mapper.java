package com.example.diplom.services.mappers.mappers2;

import com.example.diplom.entities.Subject;
import com.example.diplom.entities.Subject;
import com.example.diplom.entities.dto.to.Subject2Dto;
import com.example.diplom.entities.dto.to.Subject2Dto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Subject2Mapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Subject.class, Subject2Dto.class)
                .addMappings(m -> m.skip(Subject2Dto::setTeacherName))
                .addMappings(m -> m.skip(Subject2Dto::setRoomName));
        modelMapper.createTypeMap(Subject2Dto.class, Subject.class)
                .setPostConverter(toEntityConverter());
    }

    private Converter<Subject2Dto, Subject> toEntityConverter(){
        return mappingContext -> {
            Subject2Dto source = mappingContext.getSource();
            Subject destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Subject, Subject2Dto> toDtoConverter() {
        return mappingContext -> {
            Subject source = mappingContext.getSource();
            Subject2Dto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Subject source, Subject2Dto destination) {

        destination.setTeacherName(source.getTeacher().getLast_name()
                + " " + source.getTeacher().getFirst_name()
                + " " + source.getTeacher().getPatronymic());

        destination.setRoomName(source.getRoom().getName());
    }

    private void mapSpecificFields(Subject2Dto source, Subject destination) {
    }


    public Subject toEntity(Subject2Dto subject2Dto){
        return Objects.isNull(subject2Dto) ? null : modelMapper.map(subject2Dto, Subject.class);
    }

    public Subject2Dto toDto(Subject subject){
        return Objects.isNull(subject) ? null : modelMapper.map(subject, Subject2Dto.class);
    }
}
