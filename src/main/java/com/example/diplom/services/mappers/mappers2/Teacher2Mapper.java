package com.example.diplom.services.mappers.mappers2;

import com.example.diplom.entities.Teacher;
import com.example.diplom.entities.dto.to.Teacher2Dto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Teacher2Mapper {

    private final ModelMapper modelMapper;

    public Teacher toEntity(Teacher2Dto teacher2Dto){
        return Objects.isNull(teacher2Dto) ? null : modelMapper.map(teacher2Dto, Teacher.class);
    }

    public Teacher2Dto toDto(Teacher teacher){
        return Objects.isNull(teacher) ? null : modelMapper.map(teacher, Teacher2Dto.class);
    }
}
