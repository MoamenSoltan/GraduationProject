package org.example.backend.controller;

import org.example.backend.dto.taskDTO.RequestTaskDTO;
import org.example.backend.dto.taskDTO.ResponseTaskDTO;
import org.example.backend.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTask(@ModelAttribute RequestTaskDTO requestTaskDTO)  {

            try {
                taskService.createTask(requestTaskDTO);
                return ResponseEntity.ok("Task created successfully");
            } catch (IOException e) {
                return ResponseEntity.status(500).body("An error occurred while creating the task: " + e.getMessage());
            }

    }
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok("Task deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the task: " + e.getMessage());
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ResponseTaskDTO>> getCourseTask(@PathVariable int courseId) {


        return ResponseEntity.ok(taskService.getAllTasksByCourseId(courseId));
    }
}
