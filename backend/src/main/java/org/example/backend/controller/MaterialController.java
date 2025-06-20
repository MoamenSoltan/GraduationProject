package org.example.backend.controller;

import org.example.backend.dto.MaterialRequestDTO;
import org.example.backend.dto.MaterialResponseDTO;
import org.example.backend.dto.MaterialUpdateDTO;
import org.example.backend.service.MaterialService;
import org.example.backend.util.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/material")
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("course/{courseId}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<String> createMaterial(
            @PathVariable long courseId ,
            @CurrentUser String instructorEmail,
            @ModelAttribute MaterialRequestDTO materialRequestDTO
    ) {
        try {
            materialService.createMaterial(courseId, instructorEmail , materialRequestDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("Material created successfully");
    }
    // get materials this allow for both instructor and student to view the material
    @GetMapping("course/{courseId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('STUDENT')")
    public ResponseEntity<List<MaterialResponseDTO>> getCourseMaterials(@PathVariable long courseId) {
        return ResponseEntity.ok(materialService.getCourseMaterials(courseId));
    }

    // get material by id this allow for both instructor and student to view the material
    @GetMapping("/{materialId}/course/{courseId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('STUDENT')")
    public ResponseEntity<MaterialResponseDTO> getMaterialById(
            @PathVariable long materialId,
            @PathVariable long courseId
    ) {
        return ResponseEntity.ok(materialService.getMaterialById(materialId, courseId));
    }

    @DeleteMapping("/{materialId}/course/{courseId}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<String> deleteMaterial(
            @PathVariable long materialId,
            @PathVariable long courseId,
            @CurrentUser String instructorEmail
    ) {
        materialService.deleteMaterial(materialId, courseId, instructorEmail);
        return ResponseEntity.ok("Material deleted successfully");
    }
    @PatchMapping("/{materialId}/course/{courseId}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<String> updateMaterial(
            @PathVariable long materialId,
            @PathVariable long courseId,
            @CurrentUser String instructorEmail,
            @ModelAttribute MaterialUpdateDTO updateDTO
    ) {
        try {
            materialService.updateMaterial(materialId, courseId, instructorEmail, updateDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Material updated successfully");
    }
}
