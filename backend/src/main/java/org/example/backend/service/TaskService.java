package org.example.backend.service;

import org.example.backend.dto.taskDTO.RequestTaskDTO;
import org.example.backend.dto.taskDTO.ResponseTaskDTO;
import org.example.backend.entity.Course;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Task;
import org.example.backend.entity.TaskSubmission;
import org.example.backend.mapper.TaskMapper;
import org.example.backend.repository.CourseRepository;
import org.example.backend.repository.InstructorRepository;
import org.example.backend.repository.TaskRepository;
import org.example.backend.repository.TaskSubmissionRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final FileService fileService;
    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final TaskSubmissionRepository taskSubmissionRepository;

    public TaskService(FileService fileService, TaskRepository taskRepository, CourseRepository courseRepository, InstructorRepository instructorRepository, TaskSubmissionRepository taskSubmissionRepository) {
        this.fileService = fileService;
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.taskSubmissionRepository = taskSubmissionRepository;
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

    public void updateTask(int taskId, RequestTaskDTO requestTaskDTO) throws IOException {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTaskName(requestTaskDTO.getTaskName());
            task.setDescription(requestTaskDTO.getDescription());
            task.setDeadline(requestTaskDTO.getDeadline());

            task.setActive(requestTaskDTO.getIsActive().equals("true")?true:false);
            task.setMaxGrade(requestTaskDTO.getMaxGrade());

            if(requestTaskDTO.getCourseId()!=null)
            {
                Optional<Course> courseOptional=courseRepository.findById(requestTaskDTO.getCourseId());
                if(courseOptional.isPresent())
                {
                    Course course=courseOptional.get();
                    task.setCourse(course);
                }
                else
                {
                    throw new IllegalArgumentException("Course not found with ID: " + requestTaskDTO.getCourseId());
                }
            }

            if (requestTaskDTO.getAttachment() != null) {
                String attachmentPath = fileService.uploadFile(requestTaskDTO.getAttachment());
                task.setAttachment(attachmentPath);
            }

            taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
    }

    public ResponseTaskDTO getTaskById(int taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            TaskMapper mapper=new TaskMapper();
            return mapper.toResponseDTO(task);
        } else {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
    }

    public List<ResponseTaskDTO> getInstructorTasks(String email) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(1);
        if (instructorOptional.isPresent()) {
            Instructor instructor = instructorOptional.get();
            List<Task> tasks = taskRepository.findTaskByInstructor(instructor);
            TaskMapper mapper=new TaskMapper();
            List<ResponseTaskDTO> taskDTOs = new ArrayList<>();
            for (Task task : tasks) {
                ResponseTaskDTO taskDTO = mapper.toResponseDTO(task);
                taskDTOs.add(taskDTO);
            }
            return taskDTOs;
        } else {
            throw new IllegalArgumentException("Instructor not found with email: " + email);
        }
    }

    public List<ResponseTaskDTO> getUpcomingDeadlines(String email) {
        Date currentDate = new Date();

       List<Task> tasks= taskRepository.findUpcomingDeadlinesForStudent(email,currentDate);
        TaskMapper mapper = new TaskMapper();
        List<ResponseTaskDTO> taskDTOs = new ArrayList<>();
        for (Task task : tasks) {
            ResponseTaskDTO taskDTO = mapper.toResponseDTO(task);
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }

    public List<ResponseTaskDTO> getPastDeadlines(String email) {
        Date currentDate = new Date();

        List<Task> tasks= taskRepository.findPastDeadlinesForStudent(email,currentDate);
        TaskMapper mapper = new TaskMapper();
        List<ResponseTaskDTO> taskDTOs = new ArrayList<>();
        for (Task task : tasks) {
            ResponseTaskDTO taskDTO = mapper.toResponseDTO(task);
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }

    public List<ResponseTaskDTO> getCompletedTasks(String email) {
        List<TaskSubmission> taskSubmissions=taskSubmissionRepository.getALLTaskSubmittedByStudent(email);
        TaskMapper mapper = new TaskMapper();
        List<ResponseTaskDTO> taskDTOs = new ArrayList<>();
        for (TaskSubmission taskSubmission : taskSubmissions) {
            ResponseTaskDTO taskDTO = mapper.toResponseDTO(taskSubmission.getTask());
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }
}
