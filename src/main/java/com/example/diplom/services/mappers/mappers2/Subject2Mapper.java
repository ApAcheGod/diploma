package com.example.diplom.services.mappers.mappers2;

import com.example.diplom.entities.Group;
import com.example.diplom.entities.Subject;
import com.example.diplom.entities.Task;
import com.example.diplom.entities.dto.to.Group2Dto;
import com.example.diplom.entities.dto.to.Subject2Dto;
import com.example.diplom.entities.dto.to.Task2Dto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Subject2Mapper {

    private final ModelMapper modelMapper;

    public Subject toEntity(Subject2Dto subject2Dto){
        return Objects.isNull(subject2Dto) ? null : modelMapper.map(subject2Dto, Subject.class);
    }

    public Subject2Dto toDto(Subject subject){
        return Objects.isNull(subject) ? null : modelMapper.map(subject, Subject2Dto.class);
    }
}
