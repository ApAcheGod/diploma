package com.example.diplom.services;

import com.example.diplom.entities.Group;
import com.example.diplom.entities.dto.GroupDto;
import com.example.diplom.repositories.GroupRepository;
import com.example.diplom.services.mappers.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService{

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Transactional
    public void save(GroupDto groupDto){
        Group group = groupMapper.toEntity(groupDto);
        groupRepository.save(group);
    }

    public void save(Group group){
        groupRepository.save(group);
    }

    public List<Group> findAll(){
        return groupRepository.findAll();
    }


    public Group findById(UUID uuid) {
        return groupRepository.getById(uuid);
    }

    public void deleteById(UUID uuid) {
        Group group = groupRepository.getById(uuid);
        group.deleteLinks();
        groupRepository.save(group);
        groupRepository.deleteById(uuid);
    }
}
