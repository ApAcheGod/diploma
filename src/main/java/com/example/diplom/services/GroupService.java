package com.example.diplom.services;

import com.example.diplom.entities.Group;
import com.example.diplom.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService{

    private final GroupRepository groupRepository;

    public void save(Group group){
        groupRepository.save(group);
    }

    public List<Group> findAll(){
        return groupRepository.findAll();
    }

//    public Group findById(UUID id) {
//        return groupRepository.findById(id).get();
//    }

    public void removeById(UUID uuid) {
        groupRepository.deleteById(uuid);
    }

    public Optional<Group> findById(UUID uuid) {
        return groupRepository.findById(uuid);
    }

    public void deleteById(UUID uuid) {
        groupRepository.deleteById(uuid);
    }
}
