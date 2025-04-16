package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.backend.dto.taskDTO.RequestTaskDTO;
import org.example.backend.dto.taskDTO.ResponseTaskDTO;
import org.example.backend.dto.taskDTO.ResponseTaskSubmissionDTO;
import org.example.backend.dto.taskDTO.UpdateGrade;
import org.example.backend.service.TaskService;
import org.example.backend.service.TaskSubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/task/instructor")
public class TaskInstructorController {
    private final TaskService taskService;
    private final TaskSubmissionService taskSubmissionService;

    public TaskInstructorController(TaskService taskService, TaskSubmissionService taskSubmissionService) {
        this.taskService = taskService;
        this.taskSubmissionService = taskSubmissionService;
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
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId) {

            taskService.deleteTask(taskId);
            return ResponseEntity.ok("Task deleted successfully");

    }

    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable int taskId, @ModelAttribute RequestTaskDTO requestTaskDTO) {
//        System.out.println("is active: "+requestTaskDTO.getIsActive());
        try {
            taskService.updateTask(taskId, requestTaskDTO);
            return ResponseEntity.ok("Task updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while updating the task: " + e.getMessage());
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseTaskDTO> getTask(@PathVariable int taskId) {

            ResponseTaskDTO task = taskService.getTaskById(taskId);
            return ResponseEntity.ok(task);

    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ResponseTaskDTO>> getCourseTask(
            @PathVariable int courseId
    ) {


        return ResponseEntity.ok(taskService.getAllTasksByCourseId(courseId));
    }


    @Operation(
            summary = "Get all tasks for instructor and adding filter to course id and sort by created at "

    )
    @GetMapping
    public ResponseEntity<List<ResponseTaskDTO>> getAllTasksForInstructor(
            @RequestParam(name = "courseId", required = false) Long courseId,
            @RequestParam(value = "sortDir", defaultValue = "asc" ,required = false) String sortDir
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<ResponseTaskDTO> tasks = taskService.getInstructorTasks(email, courseId,  sortDir);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/submissions/{courseId}/{taskId}")
    public ResponseEntity<List<ResponseTaskSubmissionDTO>> getSubmittedTasksForCourseTask(@PathVariable int courseId, @PathVariable int taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<ResponseTaskSubmissionDTO> submissions = taskSubmissionService.getSubmittedTasksForInstructorCourseTask(email, courseId, taskId);
        return ResponseEntity.ok(submissions);
    }

    @PutMapping("/submissions/{courseId}/{taskId}/grade")
    public ResponseEntity<String> gradeTaskSubmission(@PathVariable int courseId, @PathVariable int taskId, @RequestBody UpdateGrade updateGrade) {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            taskSubmissionService.gradeTaskSubmission(updateGrade,email);
            return ResponseEntity.ok("Grade updated successfully");

    }

}
