package org.example.backend.service;

import org.example.backend.dto.MailBody;
import org.example.backend.dto.taskDTO.RequestTaskDTO;
import org.example.backend.dto.taskDTO.ResponseTaskDTO;
import org.example.backend.entity.*;
import org.example.backend.mapper.TaskMapper;
import org.example.backend.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class TaskService {
    private final FileService fileService;
    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final TaskSubmissionRepository taskSubmissionRepository;
    private final StudentRepository studentRepository;
    private final MailService mailService;

    public TaskService(FileService fileService, TaskRepository taskRepository, CourseRepository courseRepository, InstructorRepository instructorRepository, TaskSubmissionRepository taskSubmissionRepository, StudentRepository studentRepository, MailService mailService) {
        this.fileService = fileService;
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.taskSubmissionRepository = taskSubmissionRepository;
        this.studentRepository = studentRepository;
        this.mailService = mailService;
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
            sendMailToStudents(course.getCourseId(),requestTaskDTO.getTaskName(),requestTaskDTO.getDeadline().toString(),course.getCourseName());

        } else {
            throw new IllegalArgumentException("Course not found with ID: " + requestTaskDTO.getCourseId());
        }


        taskRepository.save(task);
    }


    @Async
    protected void sendMailToStudents(Long courseId, String taskName, String deadline, String courseName) {
        List<Student> students =studentRepository.getStudentsInCourse(courseId);
        for (Student student:students)
        {
            mailService.sendEmail(createMail(student.getUser().getEmail(),courseName,taskName,deadline));
            System.out.println("Sending mail to student: " + student.getUser().getEmail());
        }
    }
    private MailBody createMail(String email,String course,String taskName ,String deadline)
    {
        MailBody mailBody =new MailBody();
        mailBody.setTo(email);
        mailBody.setSubject("new task");
        mailBody.setText("this task "+taskName+" for course "+course+" is added\n" +
                "the deadline for task  \n" + deadline
                );
        return mailBody;
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
            boolean isSubmitted=false;
            ResponseTaskDTO taskDTO = mapper.toResponseDTO(task);
            for (TaskSubmission taskSubmission: task.getTaskSubmission())
            {
                if(taskSubmission.getTask().getId()==task.getId())
                {
                    taskDTO.setStatus("submitted");
                    isSubmitted=true;
                    break;

                }

            }

            if(!isSubmitted)
            {
                taskDTO.setStatus("unsubmitted");
            }




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

    public List<ResponseTaskDTO> getInstructorTasks(String email,Long courseId,String sortDir) {
        Optional<Instructor> instructorOptional = instructorRepository.findByEmail(email);
        System.out.println("instructor email: "+ email);

        if (instructorOptional.isPresent()) {
            Instructor instructor = instructorOptional.get();
            System.out.println("courses : "+ Arrays.toString(instructor.getCourses().toArray()));


            List<Task> tasks=null;
            if(sortDir.equalsIgnoreCase("desc"))
            {
                tasks = taskRepository.findTaskByInstructorDesc(instructor, courseId);
            }
            else
            {
                tasks = taskRepository.findTaskByInstructorAsc(instructor, courseId);
            }


            System.out.println("tasks: "+ tasks.get(0).getTaskName());
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

    public List<ResponseTaskDTO> getUpcomingDeadlines(String email,Long courseId) {
        LocalDate currentDate=LocalDate.now();

        Course course=courseRepository.findById(courseId).
                orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        List<Task> tasks= taskRepository.findUpcomingDeadlinesForStudentAndCourse(email,currentDate,courseId);
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

    public List<ResponseTaskDTO> getPastDeadlines(String email,Long courseId) {
        LocalDate currentDate =LocalDate.now();

        Course course=courseRepository.findById(courseId).
                orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        List<Task> tasks= taskRepository.findPastDeadlinesForStudent(email,currentDate,courseId);
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

    public List<ResponseTaskDTO> getCompletedTasks(String email,Long courseId) {
        Course course=courseRepository.findById(courseId).
                orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        List<TaskSubmission> taskSubmissions=taskSubmissionRepository.getALLTaskSubmittedByStudent(email,courseId);
        TaskMapper mapper = new TaskMapper();
        List<ResponseTaskDTO> taskDTOs = new ArrayList<>();
        for (TaskSubmission taskSubmission : taskSubmissions) {
            ResponseTaskDTO taskDTO = mapper.toResponseDTO(taskSubmission.getTask());
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }
}
