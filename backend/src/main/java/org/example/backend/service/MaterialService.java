package org.example.backend.service;

import org.example.backend.dto.MaterialRequestDTO;
import org.example.backend.dto.MaterialResponseDTO;
import org.example.backend.dto.MaterialUpdateDTO;
import org.example.backend.entity.Course;
import org.example.backend.entity.Material;
import org.example.backend.repository.CourseRepository;
import org.example.backend.repository.MaterialRepository;
import org.example.backend.util.FileResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MaterialService  {
    private final MaterialRepository materialRepository;
    private final CourseRepository courseRepository;
    private final FileService fileService;

    public MaterialService(MaterialRepository materialRepository, CourseRepository courseRepository, FileService fileService) {
        this.materialRepository = materialRepository;
        this.courseRepository = courseRepository;
        this.fileService = fileService;
    }

    public String createMaterial(Long courseId , String instructorEmail, MaterialRequestDTO materialRequestDTO) throws IOException {
        Course  course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if(!course.getInstructor().getUser().getEmail().equals(instructorEmail))
        {
            throw new RuntimeException("You are not authorized to create material for this course");
        }

        Material material = new Material();
        material.setTitle(materialRequestDTO.getTitle());
        material.setDescription(materialRequestDTO.getDescription());
        String filePath=fileService.uploadFile(materialRequestDTO.getFilePath());
        material.setFilePath(filePath);
        material.setCourse(course);
        materialRepository.save(material);

        return "Material created successfully";
    }

    public List<MaterialResponseDTO> getCourseMaterials(long courseId) {
         final FileResponse fileRes = new FileResponse();
        List<Material> materials = materialRepository.findByCourseId(courseId);
        if (materials.isEmpty()) {
            throw new RuntimeException("No materials found for this course");
        }

        return materials.stream()
                .map(material -> {
                    MaterialResponseDTO responseDTO = new MaterialResponseDTO();
                    responseDTO.setMaterialId(material.getMaterialId());
                    responseDTO.setTitle(material.getTitle());
                    responseDTO.setDescription(material.getDescription());
                    responseDTO.setFilePath(fileRes.getFileName(material.getFilePath()));
                    responseDTO.setCreatedAt(material.getCreatedAt().toString());
                    return responseDTO;
                })
                .toList();
    }

    public MaterialResponseDTO getMaterialById(long materialId, long courseId) {
        final FileResponse fileRes = new FileResponse();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        if (material.getCourse().getCourseId() != courseId) {
            throw new RuntimeException("Material does not belong to this course");
        }

        MaterialResponseDTO responseDTO = new MaterialResponseDTO();
        responseDTO.setMaterialId(material.getMaterialId());
        responseDTO.setTitle(material.getTitle());
        responseDTO.setDescription(material.getDescription());
        responseDTO.setFilePath(fileRes.getFileName(material.getFilePath()));
        responseDTO.setCreatedAt(material.getCreatedAt().toString());

        return responseDTO;
    }

    public void deleteMaterial(long materialId, long courseId, String instructorEmail) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getInstructor().getUser().getEmail().equals(instructorEmail)) {
            throw new RuntimeException("You are not authorized to delete material for this course");
        }

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        if (material.getCourse().getCourseId() != courseId) {
            throw new RuntimeException("Material does not belong to this course");
        }

        material.setDeleted(true);
        materialRepository.delete(material);
    }

    public void updateMaterial(long materialId, long courseId, String instructorEmail, MaterialUpdateDTO materialRequestDTO) throws IOException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getInstructor().getUser().getEmail().equals(instructorEmail)) {
            throw new RuntimeException("You are not authorized to update material for this course");
        }

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        if (material.getCourse().getCourseId() != courseId) {
            throw new RuntimeException("Material does not belong to this course");
        }

        if(materialRequestDTO.getTitle()!=null)
        {
            material.setTitle(materialRequestDTO.getTitle());
        }
        if(materialRequestDTO.getDescription()!=null)
        {
            material.setDescription(materialRequestDTO.getDescription());
        }
        MultipartFile file = materialRequestDTO.getFilePath();
        if(file!=null)
        {
            String filePath = fileService.uploadFile(file);
            material.setFilePath(filePath);
        }


        materialRepository.save(material);
    }
}
