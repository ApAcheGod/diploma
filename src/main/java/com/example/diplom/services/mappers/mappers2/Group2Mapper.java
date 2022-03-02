package com.example.diplom.services.mappers.mappers2;

import com.example.diplom.entities.Group;
import com.example.diplom.entities.dto.to.Group2Dto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Group2Mapper {

    private final ModelMapper modelMapper;

    public Group toEntity(Group2Dto group2Dto){
        return Objects.isNull(group2Dto) ? null : modelMapper.map(group2Dto, Group.class);
    }

    public Group2Dto toDto(Group group){
        return Objects.isNull(group) ? null : modelMapper.map(group, Group2Dto.class);
    }

}
