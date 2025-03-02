package org.example.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class FileService {
    @Value("${file.upload}")
    private String UPLOAD_DIRECTORY;

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

        String path = UPLOAD_DIRECTORY;
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String fullPath = path + File.separator + uniqueFileName;

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
}
