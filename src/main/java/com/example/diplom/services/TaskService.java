package com.example.diplom.services;

import com.example.diplom.entities.Task;
import com.example.diplom.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public void save(Task task) {
        taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(UUID uuid) {
        return taskRepository.getById(uuid);
    }
}
