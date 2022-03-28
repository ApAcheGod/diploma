package com.example.diplom.services.mappers.mappers2;

import com.example.diplom.entities.Teacher;
import com.example.diplom.entities.dto.to.Teacher2Dto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Teacher2Mapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Teacher.class, Teacher2Dto.class)
                .addMappings(m -> m.skip(Teacher2Dto::setTeacherName));
        modelMapper.createTypeMap(Teacher2Dto.class, Teacher.class)
                .setPostConverter(toEntityConverter());
    }

    private Converter<Teacher2Dto, Teacher> toEntityConverter(){
        return mappingContext -> {
            Teacher2Dto source = mappingContext.getSource();
            Teacher destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Teacher, Teacher2Dto> toDtoConverter() {
        return mappingContext -> {
            Teacher source = mappingContext.getSource();
            Teacher2Dto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Teacher source, Teacher2Dto destination) {

        destination.setTeacherName(source.getTeacherName());
    }

    private void mapSpecificFields(Teacher2Dto source, Teacher destination) {
    }


    public Teacher toEntity(Teacher2Dto teacher2Dto){
        return Objects.isNull(teacher2Dto) ? null : modelMapper.map(teacher2Dto, Teacher.class);
    }

    public Teacher2Dto toDto(Teacher teacher){
        return Objects.isNull(teacher) ? null : modelMapper.map(teacher, Teacher2Dto.class);
    }
}
