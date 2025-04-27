package org.example.backend.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.example.backend.dto.studentDto.StudentCourseDTO;
import org.example.backend.dto.studentDto.StudentCourseGradeDTO;
import org.example.backend.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    @Value("${file.upload}")
    private String UPLOAD_DIRECTORY;
    @Value("${file.BASE_URL}")
    private String BASE_URL;

    public String saveFile(MultipartFile file, String prefix) throws IOException {
        if (file != null && !file.isEmpty()) {
            // [FIXED]: Generate a unique filename to prevent overwriting
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = prefix + "_" + System.currentTimeMillis() + fileExtension;
            String filePath = UPLOAD_DIRECTORY + File.separator + uniqueFileName;

            // Create the directory if it doesn't exist
            File uploadDir = new File(UPLOAD_DIRECTORY);
            if (!uploadDir.exists()) {
                if (uploadDir.mkdirs()) {
                    System.out.println("Directory created: " + UPLOAD_DIRECTORY);
                } else {
                    throw new IOException("Failed to create directory: " + UPLOAD_DIRECTORY);
                }
            }

            // Save the file
            File dest = new File(filePath);
            file.transferTo(dest);
            return filePath;
        }
        return null;
    }

    public String uploadFile(MultipartFile file ) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("File cannot be null or empty");
        }

        File directory = new File(UPLOAD_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("File name cannot be null");
        }
        String sanitizedFilename = originalFilename.replaceAll("\\s+", "_");
        sanitizedFilename.replaceAll("[^a-zA-Z0-9._-]", "");
        String uniqueFileName = UUID.randomUUID() + "_" + sanitizedFilename;
        String fullPath = UPLOAD_DIRECTORY + "/" + uniqueFileName;

        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(fullPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return fullPath;
    }

    public  String getFileName(String filePath) {
        return filePath != null ?BASE_URL+Paths.get(filePath).getFileName().toString() : null;
    }

    public ByteArrayInputStream load(List<StudentCourseDTO> dtoList)  {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try(CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))){

            writer.writeNext(new String[]{"Student ID", "Username", "Email", "Degree"});

            for (StudentCourseDTO dto : dtoList) {
                if(dto.getDegree()==null)
                {
                    dto.setDegree(0.0);
                }
                String[] row = new String[]{
                        dto.getStudentId().toString(),
                        dto.getUsername(),
                        dto.getEmail(),
                        dto.getDegree().toString()
                };
                writer.writeNext(row);
            }
            writer.flush();
        }catch (IOException e)
        {
            throw new RuntimeException("Failed to generate CSV file", e);
        }



        return new ByteArrayInputStream(outputStream.toByteArray());
    }


    public ByteArrayInputStream loadAllDegrees(List<StudentCourseGradeDTO> dtoList)  {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try(CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))){

            writer.writeNext(new String[]{"Student ID", "Username", "Email", "Final Exam Degree" ,"Quizzes Degree","Tasks Degree"});

            for (StudentCourseGradeDTO dto : dtoList) {
                if(dto.getFinalDegree()==null)
                {
                    dto.setFinalDegree(0.0);
                }
                if(dto.getTotalQuizGrade()==null)
                {
                    dto.setTotalQuizGrade(0.0);
                }
                if(dto.getTotalTaskGrade()==null)
                {
                    dto.setTotalTaskGrade(0.0);
                }
                String[] row = new String[]{
                        dto.getStudentId().toString(),
                        dto.getUsername(),
                        dto.getEmail(),
                        dto.getFinalDegree().toString(),
                        dto.getTotalQuizGrade().toString(),
                        dto.getTotalTaskGrade().toString()
                };
                writer.writeNext(row);
            }
            writer.flush();
        }catch (IOException e)
        {
            throw new RuntimeException("Failed to generate CSV file", e);
        }



        return new ByteArrayInputStream(outputStream.toByteArray());
    }



    public ByteArrayInputStream loadStudentForAdmin(List<Student> students)
    {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        try(PrintWriter printWriter =new PrintWriter(outputStream))
        {
            printWriter.println("Student ID ,Username ,email");

            for (Student student:students)
            {
                String[] row = new String[]{
                        student.getStudentId().toString(),
                        student.getUser().getFirstName()+" "+student.getUser().getLastName(),
                        student.getUser().getEmail()
                };
                printWriter.println(String.join(",", row));
            }
            printWriter.flush();
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public List<StudentCourseDTO> parseCSV(MultipartFile file)
    {
        List<StudentCourseDTO> dtos=new ArrayList<>();
        try(CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream())))
        {
            String[] nextLine;
            // Skip the header line
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                Long studentId = Long.parseLong(nextLine[0]);
                String username = nextLine[1];
                String email = nextLine[2];
                Double degree = Double.parseDouble(nextLine[3]);

                StudentCourseDTO dto = new StudentCourseDTO(studentId, username, email, degree);
                dtos.add(dto);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }

        return dtos;
    }
}
