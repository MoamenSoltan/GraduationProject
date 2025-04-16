package org.example.backend.service;

import org.example.backend.dto.courseDto.CourseDTO;
import org.example.backend.dto.instructorDto.InstructorProfile;
import org.example.backend.dto.instructorDto.InstructorRequestDTO;
import org.example.backend.dto.instructorDto.InstructorResponseDTO;
import org.example.backend.dto.instructorDto.UpdateInstructor;
import org.example.backend.dto.semesterDto.SemesterDTO;
import org.example.backend.dto.studentDto.StudentCourseDTO;
import org.example.backend.entity.Department;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.Role;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.InstructorMapper;
import org.example.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    private static final Logger log = LoggerFactory.getLogger(InstructorService.class);
    private final InstructorRepository instructorRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final FileService fileService;
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    public InstructorService(InstructorRepository instructorRepository,
                             DepartmentRepository departmentRepository,
                             RoleRepository roleRepository, FileService fileService, StudentRepository studentRepository, StudentCourseRepository studentCourseRepository) {
        this.instructorRepository = instructorRepository;

        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.fileService = fileService;
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

//    @Transactional
//    public Instructor insertInstructor(InstructorDTO dto) {
//
//        Instructor instructor = instructorMapper.mapToInstructor(dto);
//        Department department = departmentRepository.getGeneralDepartment(dto.getDepartmentName())
//                .orElseThrow(() -> new ResourceNotFound("Department","department_name" , dto.getDepartmentName()));
//        instructor.setDepartment(department);
//
//        Role role = roleRepository.getInstructorRole()
//                .orElseThrow(() -> new ResourceNotFound("Role","role_id",2));
//        instructor.getUser().addRole(role);
//
//
//
//
//        Instructor instructor1=instructorRepository.save(instructor);
//        if(dto.isHeadOfDepartment())
//        {
//            department.setHeadOfDepartment(instructor1);
//        }
//
//        return instructor;
//    }


//    @Transactional
//    public String insertHeadOfDepartment(String email, DepartmentName departmentName)
//    {
//        Instructor instructor = instructorRepository.getByEmail(email);
//        Department department = departmentRepository.getGeneralDepartment(departmentName).get();
//
//        department.setHeadOfDepartment(instructor);
//        return "inserted";
//    }
    public InstructorResponseDTO createInstructor(InstructorRequestDTO requestDTO)
    {
        if (instructorRepository.existsByUserEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + requestDTO.getEmail());
        }
        Instructor instructor = InstructorMapper.requestToEntity(requestDTO);
        Role role = roleRepository.getInstructorRole()
                .get();
        instructor.getUser().addRole(role);

        Department department = departmentRepository.findById(requestDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFound("Department", "id", requestDTO.getDepartmentId()));

        instructor.setDepartment(department);
        if (requestDTO.getManagedDepartmentId() != null) {
            Department managedDepartment = departmentRepository.findById(requestDTO.getManagedDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Managed Department not found with id: " + requestDTO.getManagedDepartmentId()));
            instructor.setManagedDepartment(managedDepartment);
            managedDepartment.setHeadOfDepartment(instructor);
        }

        Instructor savedInstructor = instructorRepository.save(instructor);

        return InstructorMapper.entityToResponseDTO(savedInstructor);
    }

    public List<InstructorResponseDTO> getAllInstructors()
    {
        List<Instructor> instructors = instructorRepository.findAll();

        List<InstructorResponseDTO> responseDTOList = new ArrayList<>();

        for (Instructor instructor:instructors)
        {
            responseDTOList.add(InstructorMapper.entityToResponseDTO(instructor));
        }

        return responseDTOList;
    }

    public InstructorResponseDTO getCoursesInstructorsForStudent(String studentEmail)
    {
        Optional<Instructor> opt = instructorRepository.getCoursesInstructorForStudent(studentEmail);
        if(!opt.isPresent())
        {
            throw new RuntimeException("you don't signed any courses");
        }

        Instructor instructor = opt.get();
//        System.out.println(instructor.getCourses().get(0).getCourseCode());
//        instructor.getCourses().forEach(c-> System.out.println(c.getCourseCode()));
        //return InstructorMapper.entityToResponseDTO(instructor,studentRepository.findStudentByEmail(studentEmail).get());
        return InstructorMapper.entityToResponseDTO(instructor);
    }

    public InstructorProfile getInstructorProfile(String email) {
        Instructor instructor=instructorRepository.getByEmail(email)
                .orElseThrow(()->new ResourceNotFound("instructor", "email", email));

        return InstructorMapper.toInstructorProfile(instructor);
    }

    public String UpdateInstructor(UpdateInstructor updateInstructor, Instructor instructor) throws IOException {
        if (updateInstructor.getFirstName() != null) {
            instructor.getUser().setFirstName(updateInstructor.getFirstName());
        }
        if (updateInstructor.getLastName() != null) {
            instructor.getUser().setLastName(updateInstructor.getLastName());
        }
        if (updateInstructor.getBio() != null) {
            instructor.setBio(updateInstructor.getBio());
        }
        if(updateInstructor.getEmail() != null) {
            instructor.getUser().setEmail(updateInstructor.getEmail());
        }
        if (updateInstructor.getPersonalImage() != null) {
            instructor.setPersonalImage(fileService.uploadFile(updateInstructor.getPersonalImage()));
        }

        instructorRepository.save(instructor);
        return "Instructor updated successfully";
    }

    public InstructorResponseDTO getInstructorById(int id) {
        Optional<Instructor> opt = instructorRepository.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFound("Instructor", "id", id);
        }
        Instructor instructor = opt.get();
        return InstructorMapper.entityToResponseDTO(instructor);
    }

    public List<InstructorResponseDTO> getInstructManagesDp() {
        List<Instructor> instructors = instructorRepository.getInstructorThatMangedDepartments().get();
        List<InstructorResponseDTO> responseDTOList = new ArrayList<>();
        for (Instructor instructor : instructors) {
            responseDTOList.add(InstructorMapper.entityToResponseDTO(instructor));
        }
        return responseDTOList;
    }

    public List<CourseDTO> getInstructorCourses(String email) {
       Optional <Instructor> instructor = instructorRepository.getByEmail(email);
        if (!instructor.isPresent()) {
            throw new ResourceNotFound("Instructor", "email", email);
        }
        List<CourseDTO> courseDTOList = new ArrayList<>();
        for (var course : instructor.get().getCourses()) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseCode(course.getCourseCode());
            courseDTO.setCourseName(course.getCourseName());
            courseDTO.setCourseId(course.getCourseId());
            courseDTO.setGrade(course.getYear());

            SemesterDTO semesterDTO =new SemesterDTO();
            semesterDTO.setYearLevel(course.getSemester().getSemesterId().getYearLevel());
            semesterDTO.setSemesterName(course.getSemester().getSemesterId().getSemesterName().toString());
            semesterDTO.setYear(course.getSemester().getStartDate().getYear());            courseDTO.setSemester(semesterDTO);
            courseDTOList.add(courseDTO);
        }
        return courseDTOList;
    }

    public List<StudentCourseDTO> getStudentsInCourse(int courseId, String email) {
        Instructor instructor = instructorRepository.getByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("Instructor", "email", email));

        int instructorId = instructor.getInstructorId();

        Optional<List<StudentCourseDTO>> students = studentRepository.getStudentInCourse(courseId, instructorId);

        return students.get();
    }

    public StudentCourseDTO getStudentInCourse(int courseId, String email, Long studentId) {
        Instructor instructor = instructorRepository.getByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("Instructor", "email", email));

        int instructorId = instructor.getInstructorId();

        Optional<StudentCourseDTO> students = studentRepository.getStudentInCourse(courseId, instructorId, studentId);

        return students.get();
    }

    public String updateStudentDegree(int courseId, MultipartFile file,String email) {

        List<StudentCourseDTO> dtos = fileService.parseCSV(file);
        Instructor instructor = instructorRepository.getByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("Instructor", "email", email));

        int instructorId = instructor.getInstructorId();

        for (StudentCourseDTO dto : dtos) {
            if(dto.getStudentId() != null && dto.getDegree()!=null )
            {
            Long studentId = dto.getStudentId();
            double degree = dto.getDegree();
            studentCourseRepository.updateStudentCourseDegree(degree,studentId,courseId,instructorId);
            }
        }

        return "degree update ";
    }
}