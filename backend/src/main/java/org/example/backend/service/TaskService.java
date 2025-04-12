package org.example.backend.service;

import org.example.backend.dto.taskDTO.RequestTaskDTO;
import org.example.backend.dto.taskDTO.ResponseTaskDTO;
import org.example.backend.entity.Course;
import org.example.backend.entity.Task;
import org.example.backend.mapper.TaskMapper;
import org.example.backend.repository.CourseRepository;
import org.example.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final FileService fileService;
    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    public TaskService(FileService fileService, TaskRepository taskRepository, CourseRepository courseRepository) {
        this.fileService = fileService;
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    public void createTask(RequestTaskDTO requestTaskDTO) throws IOException {

        String attachmentPath=fileService.uploadFile(requestTaskDTO.getAttachment());

        Task task = TaskMapper.toEntity(requestTaskDTO);
        task.setAttachment(attachmentPath);
        task.setActive(true);
        System.out.println("is active: "+requestTaskDTO.getIsActive());

        Optional<Course> courseOptional = courseRepository.findById(requestTaskDTO.getCourseId());
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            task.setCourse(course);
        } else {
            throw new IllegalArgumentException("Course not found with ID: " + requestTaskDTO.getCourseId());
        }

        taskRepository.save(task);
    }

    public void deleteTask(int taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
        taskRepository.deleteById(taskId);
    }

    public List<ResponseTaskDTO> getAllTasksByCourseId(int courseId) {
        List<Task> tasks = taskRepository.findTaskByCourseId(courseId);
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("No tasks found for course ID: " + courseId);
        }

        TaskMapper mapper=new TaskMapper();
        List<ResponseTaskDTO> taskDTOs = new ArrayList<>();
        for (Task task : tasks) {
            ResponseTaskDTO taskDTO = mapper.toResponseDTO(task);
            taskDTOs.add(taskDTO);
        }

        return taskDTOs;
    }
}
