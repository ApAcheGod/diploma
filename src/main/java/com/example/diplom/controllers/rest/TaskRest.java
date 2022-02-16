package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Task;
import com.example.diplom.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaskRest {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> allSubjects(){
        return taskService.findAll();
    }

    @GetMapping("/task/{id}")
    public Task oneSubject(@PathVariable("id")UUID uuid){
        return taskService.findById(uuid);
    }

    @PostMapping("/task")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Task task) {
        taskService.save(task);
    }

    @PutMapping(value = "/task/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Task task) {
        taskService.save(task);
    }

    @DeleteMapping(value = "task/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        taskService.deleteById(id);
    }
}
