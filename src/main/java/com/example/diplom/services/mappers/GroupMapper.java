package com.example.diplom.services.mappers;

import com.example.diplom.entities.Group;
import com.example.diplom.entities.dto.GroupDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final ModelMapper modelMapper;

    public Group toEntity(GroupDto groupDto){
        return Objects.isNull(groupDto) ? null : modelMapper.map(groupDto, Group.class);
    }

    public GroupDto toDto(Group group){
        return Objects.isNull(group) ? null : modelMapper.map(group, GroupDto.class);
    }
}
