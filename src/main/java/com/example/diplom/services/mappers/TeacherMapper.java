package com.example.diplom.services.mappers;

import com.example.diplom.entities.Teacher;
import com.example.diplom.entities.dto.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TeacherMapper {

    private final ModelMapper modelMapper;

    public Teacher toEntity(TeacherDto teacherDto){
        return Objects.isNull(teacherDto) ? null : modelMapper.map(teacherDto, Teacher.class);
    }

    public TeacherDto toDto(Teacher teacher){
        return Objects.isNull(teacher) ? null : modelMapper.map(teacher, TeacherDto.class);
    }
}
