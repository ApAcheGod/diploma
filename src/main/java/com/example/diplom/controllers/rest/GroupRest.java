package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Group;
import com.example.diplom.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GroupRest {

    private final GroupService groupService;

    @GetMapping("/groups")
    public List<Group> allGroups(){
        return groupService.findAll();
    }

    @GetMapping("/group/{id}")
    public Group oneGroup(@PathVariable("id") UUID uuid){
        return groupService.findById(uuid);
    }

    @PostMapping("/group")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Group group) {
        groupService.save(group);
    }

    @PutMapping("/group/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Group group) {
        groupService.save(group);
    }

    @DeleteMapping("group/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        groupService.deleteById(id);
    }
}
