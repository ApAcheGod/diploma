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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GroupRest {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @GetMapping("/groups")
    public List<GroupDto> allGroups(){
        return groupService.findAll().stream().map(groupMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<GroupDto> oneGroup(@PathVariable("id") UUID uuid){
        return new ResponseEntity<>(groupMapper.toDto(groupService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<GroupDto> create(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.toEntity(groupDto);
        groupService.save(group);
        return new ResponseEntity<>(groupMapper.toDto(group), HttpStatus.CREATED);
    }

    @PutMapping("/group")
    public ResponseEntity<GroupDto> update(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.toEntity(groupDto);
        groupService.save(group);
        return new ResponseEntity<>(groupMapper.toDto(group), HttpStatus.OK);
    }

    @DeleteMapping("group/{id}")
    public void delete(@PathVariable("id") UUID id) {
        groupService.deleteById(id);
    }
}
