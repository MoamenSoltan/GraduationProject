package org.example.backend.dto.submissionDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class SubmissionImages {
    private MultipartFile highSchoolCertificate;
    private MultipartFile idPhoto;
    private MultipartFile personalPhoto;
}
