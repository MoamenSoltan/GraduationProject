package org.example.backend.service;

import org.example.backend.entity.Student;
import org.example.backend.repository.StudentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PromotionScheduler {
    
    private final StudentRepository studentRepository;
    private final PromotionService promotionService;

    public PromotionScheduler(StudentRepository studentRepository, PromotionService promotionService) {
        this.studentRepository = studentRepository;
        this.promotionService = promotionService;
    }

    /**
     * Automatic end-of-year student promotion process.
     * Runs on June 1st at 2:00 AM every year.
     * 
     * Cron expression: "0 0 2 1 6 *"
     * - 0 = second (0)
     * - 0 = minute (0)
     * - 2 = hour (2 AM)
     * - 1 = day of month (1st)
     * - 6 = month (June)
     * - * = day of week (any day)
     */
    @Scheduled(cron = "0 0 2 1 6 *")
    public void endOfYearPromotion() {
        System.out.println("Starting automatic end-of-year student promotion process...");
        
        try {
            // Get all students
            List<Student> allStudents = studentRepository.findAll();
            List<Long> studentIds = allStudents.stream()
                .map(Student::getStudentId)
                .collect(Collectors.toList());
            
            System.out.println("Found " + studentIds.size() + " students to process for promotion");
            
            // Process promotion for all students
            promotionService.checkAndPromoteMultipleStudents(studentIds);
            
            System.out.println("Automatic end-of-year promotion process completed successfully");
            
        } catch (Exception e) {
            System.err.println("Error during automatic promotion process: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Alternative: End of Spring semester promotion (May 15th at 2:00 AM)
     * This runs after Spring semester grades are typically finalized
     */
    @Scheduled(cron = "0 0 2 15 5 *")
    public void endOfSpringSemesterPromotion() {
        System.out.println("Starting end-of-Spring semester student promotion process...");
        
        try {
            List<Student> allStudents = studentRepository.findAll();
            List<Long> studentIds = allStudents.stream()
                .map(Student::getStudentId)
                .collect(Collectors.toList());
            
            System.out.println("Found " + studentIds.size() + " students to process for Spring semester promotion");

            promotionService.checkAndPromoteMultipleStudents(studentIds);
            
            System.out.println("End-of-Spring semester promotion process completed successfully");
            
        } catch (Exception e) {
            System.err.println("Error during Spring semester promotion process: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test method that can be called manually for testing purposes.
     * This method is not scheduled and can be called via admin endpoint if needed.
     */
    public void manualPromotionProcess() {
        System.out.println("Starting manual student promotion process...");
        
        try {
            List<Student> allStudents = studentRepository.findAll();
            List<Long> studentIds = allStudents.stream()
                .map(Student::getStudentId)
                .collect(Collectors.toList());
            
            System.out.println("Found " + studentIds.size() + " students to process for manual promotion");
            
            promotionService.checkAndPromoteMultipleStudents(studentIds);
            
            System.out.println("Manual promotion process completed successfully");
            
        } catch (Exception e) {
            System.err.println("Error during manual promotion process: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 