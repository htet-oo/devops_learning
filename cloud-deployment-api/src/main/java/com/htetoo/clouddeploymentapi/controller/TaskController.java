
package com.htetoo.clouddeploymentapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htetoo.clouddeploymentapi.dto.TaskRequest;
import com.htetoo.clouddeploymentapi.dto.TaskResponse;
import com.htetoo.clouddeploymentapi.entity.Task;
import com.htetoo.clouddeploymentapi.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request) {

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        Task savedTask = taskService.saveTask(task);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(savedTask));
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {

        return taskService.getAllTasks()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id) {

        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(toResponse(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {

        return taskService.getTaskById(id)
                .map(task -> {
                    task.setTitle(request.getTitle());
                    task.setDescription(request.getDescription());

                    Task updatedTask = taskService.saveTask(task);

                    return ResponseEntity.ok(toResponse(updatedTask));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id) {

        if (taskService.getTaskById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }

    private TaskResponse toResponse(Task task) {

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted()
        );
    }
}
