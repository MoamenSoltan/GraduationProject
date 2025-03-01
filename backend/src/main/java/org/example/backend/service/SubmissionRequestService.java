package org.example.backend.service;

import org.example.backend.dto.SubmissionRequestDto;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.enums.RoleType;
import org.example.backend.mapper.SubmissionRequestMapper;
import org.example.backend.repository.SubmissionReqRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Transactional
public class SubmissionRequestService {
    private final SubmissionReqRepository reqRepository;
    private final SubmissionRequestMapper submissionRequestMapper;
    @Value("${file.upload}")
    private String UPLOAD_DIRECTORY;

    public SubmissionRequestService(SubmissionReqRepository reqRepository, SubmissionRequestMapper submissionRequestMapper) {
        this.reqRepository = reqRepository;
        this.submissionRequestMapper = submissionRequestMapper;
    }



    public SubmissionRequest saveSubmissionRequest(SubmissionRequestDto dto) throws IOException {
        SubmissionRequest request = submissionRequestMapper.mapToEntity(dto);

        String idPhotoPath = saveFile(dto.getIdPhoto(), "idPhoto");
        String personalPhotoPath = saveFile(dto.getPersonalPhoto(), "personalPhoto");
        String certificatePath = saveFile(dto.getHighSchoolCertificate(), "certificate");

        request.setPersonalPhoto(personalPhotoPath);
        request.setIdPhoto(idPhotoPath);
        request.setHighSchoolCertificate(certificatePath);

        return reqRepository.save(request);
    }


    private String saveFile(MultipartFile file, String prefix) throws IOException {
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

}
