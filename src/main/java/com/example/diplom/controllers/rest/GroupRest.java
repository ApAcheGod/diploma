package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Group;
import com.example.diplom.entities.dto.GroupDto;
import com.example.diplom.services.GroupService;
import com.example.diplom.services.mappers.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GroupRest {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @GetMapping("/groups")
    public List<Group> allGroups(){
        return groupService.findAll();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<GroupDto> oneGroup(@PathVariable("id") UUID uuid){
        return new ResponseEntity<>(groupMapper.toDto(groupService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<Group> create(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.toEntity(groupDto);
        groupService.save(group);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @PutMapping("/group/{id}")
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Group group) {
        groupService.save(group);
    }

    @DeleteMapping("group/{id}")
    public void delete(@PathVariable("id") UUID id) {
        groupService.deleteById(id);
    }
}
