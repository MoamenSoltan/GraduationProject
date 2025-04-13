package org.example.backend.service;

import org.example.backend.dto.taskDTO.ResponseTaskSubmissionDTO;
import org.example.backend.dto.taskDTO.TaskSubmissionDTO;
import org.example.backend.dto.taskDTO.UpdateGrade;
import org.example.backend.entity.Student;
import org.example.backend.entity.Task;
import org.example.backend.entity.TaskSubmission;
import org.example.backend.mapper.TaskSubmissionMapper;
import org.example.backend.repository.StudentRepository;
import org.example.backend.repository.TaskRepository;
import org.example.backend.repository.TaskSubmissionRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskSubmissionService {
    private final TaskRepository taskRepository;
    private final StudentRepository studentRepository;
    private final FileService fileService;
    private final TaskSubmissionRepository taskSubmissionRepository;

    public TaskSubmissionService(TaskRepository taskRepository, StudentRepository studentRepository, FileService fileService, TaskSubmissionRepository taskSubmissionRepository) {
        this.taskRepository = taskRepository;
        this.studentRepository = studentRepository;
        this.fileService = fileService;
        this.taskSubmissionRepository = taskSubmissionRepository;
    }

    public void submitTask(int taskId, String email, TaskSubmissionDTO taskSubmissionDTO) throws IOException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.isActive()) {
            throw new IllegalStateException("Task is not active");
        }

        Student student = studentRepository.findStudentByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        TaskSubmission taskSubmission = TaskSubmissionMapper.toEntity(taskSubmissionDTO);
        taskSubmission.setTask(task);
        taskSubmission.setStudent(student);

        String filePath = fileService.uploadFile(taskSubmissionDTO.getAttachment());
        taskSubmission.setAttachment(filePath);

        taskSubmissionRepository.save(taskSubmission);
    }

    public List<ResponseTaskSubmissionDTO> getAllTaskSubmittedByStudent(String email) {
        List<TaskSubmission> taskSubmissions = taskSubmissionRepository.getALLTaskSubmittedByStudent(email);
        List<ResponseTaskSubmissionDTO> responseTaskSubmissionDTOs = new ArrayList<>();
        TaskSubmissionMapper mapper =new TaskSubmissionMapper();
        for (TaskSubmission taskSubmission : taskSubmissions) {
            ResponseTaskSubmissionDTO responseTaskSubmissionDTO = mapper.toResponseDTO(taskSubmission);
            responseTaskSubmissionDTOs.add(responseTaskSubmissionDTO);
        }
        return responseTaskSubmissionDTOs;
    }

    public List<ResponseTaskSubmissionDTO> getSubmittedTasksForInstructorCourseTask(String email, int courseId, int taskId) {
        List<TaskSubmission> submissions = taskSubmissionRepository.findSubmissionsByInstructorCourseAndTask(email, courseId, taskId);
        TaskSubmissionMapper mapper = new TaskSubmissionMapper();
        List<ResponseTaskSubmissionDTO> responseDTOs = new ArrayList<>();
        for (TaskSubmission submission : submissions) {
            responseDTOs.add(mapper.toResponseDTO(submission));
        }
        return responseDTOs;
    }

    public void gradeTaskSubmission(UpdateGrade updateGrade,String email) {

            TaskSubmission taskSubmission = taskSubmissionRepository.findById(updateGrade.getSubmissionId())
                    .orElseThrow(() -> new IllegalArgumentException("Task submission not found with ID: " + updateGrade.getSubmissionId()));

            double grade = updateGrade.getGrade();
            if (grade < 0 || grade > taskSubmission.getTask().getMaxGrade()) {
                throw new IllegalArgumentException("Grade must be between 0 and the maximum grade for the task.");
            }

            if(!taskSubmission.getTask().getCourse().getInstructor().getUser().getEmail().equals( email)) {
                throw new IllegalArgumentException("You are not authorized to grade this submission.");
            }
            taskSubmission.setGrade(grade);
            taskSubmissionRepository.save(taskSubmission);

    }
}
