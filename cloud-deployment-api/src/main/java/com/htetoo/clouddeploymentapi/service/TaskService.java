
package com.htetoo.clouddeploymentapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.htetoo.clouddeploymentapi.dao.TaskRepository;
import com.htetoo.clouddeploymentapi.entity.Task;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
