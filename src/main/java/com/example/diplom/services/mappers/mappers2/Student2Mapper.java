package com.example.diplom.services.mappers.mappers2;

import com.example.diplom.entities.Group;
import com.example.diplom.entities.Student;
import com.example.diplom.entities.dto.to.Group2Dto;
import com.example.diplom.entities.dto.to.Student2Dto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Student2Mapper {

    private final ModelMapper modelMapper;

    public Student toEntity(Student2Dto student2Dto){
        return Objects.isNull(student2Dto) ? null : modelMapper.map(student2Dto, Student.class);
    }

    public Student2Dto toDto(Student student){
        return Objects.isNull(student) ? null : modelMapper.map(student, Student2Dto.class);
    }

}
