package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Task;
import com.example.diplom.entities.dto.TaskDto;
import com.example.diplom.services.TaskService;
import com.example.diplom.services.mappers.TaskMapper;
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
public class TaskRest {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("/tasks")
    public List<TaskDto> allSubjects(){
        return taskService.findAll().stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> oneSubject(@PathVariable("id")UUID uuid){
        return new ResponseEntity<>(taskMapper.toDto(taskService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/task")
    public ResponseEntity<Task> create(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        taskService.save(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PutMapping(value = "/task")
    public ResponseEntity<TaskDto> update(@RequestBody TaskDto taskDto) {
        Task task = taskService.findById(taskDto.getId());
        task.setText(taskDto.getText());
        task.setTaskType(taskDto.getTaskType());
        task.setMax_rating(taskDto.getMax_rating());
        task.setMin_rating(taskDto.getMin_rating());
        task.setIsTemporal(taskDto.getIsTemporal());
        task.setIsMandatory(taskDto.getIsMandatory());
        task.setCount_of_attempts(taskDto.getCount_of_attempts());
        task.setLast_date_of_delivery(taskDto.getLast_date_of_delivery());
        taskService.save(task);
        return new ResponseEntity<>(taskMapper.toDto(task), HttpStatus.OK);
    }

    @DeleteMapping(value = "task/{id}")
    public void delete(@PathVariable("id") UUID id) {
        taskService.deleteById(id);
    }
}
