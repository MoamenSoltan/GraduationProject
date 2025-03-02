package org.example.backend.service;

import org.example.backend.dto.SubmissionRequestDto;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.entity.User;
import org.example.backend.enums.AdmissionStatus;
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
    public final FileService fileService;

    public SubmissionRequestService(SubmissionReqRepository reqRepository, SubmissionRequestMapper submissionRequestMapper, FileService fileService) {
        this.reqRepository = reqRepository;
        this.submissionRequestMapper = submissionRequestMapper;
        this.fileService = fileService;
    }



    public SubmissionRequest saveSubmissionRequest(SubmissionRequestDto dto) throws IOException {
        SubmissionRequest request = submissionRequestMapper.mapToEntity(dto);

//        String idPhotoPath = fileService.saveFile(dto.getIdPhoto(), "idPhoto");
//        String personalPhotoPath = fileService.saveFile(dto.getPersonalPhoto(), "personalPhoto");
//        String certificatePath = fileService.saveFile(dto.getHighSchoolCertificate(), "certificate");

//        System.out.println("saving photo");
//        String idPhotoPath=fileService.saveFile(dto.getIdPhoto(),"idPhoto");
//        String personalPhotoPath = fileService.saveFile(dto.getPersonalPhoto(),"personalPhoto");
//        String certificatePath = fileService.saveFile(dto.getHighSchoolCertificate(),"certificatePath");

        //
        String idPhotoPath=fileService.uploadFile(dto.getIdPhoto());
        String personalPhotoPath = fileService.uploadFile(dto.getPersonalPhoto());
        String certificatePath = fileService.uploadFile(dto.getHighSchoolCertificate());

        //
        System.out.println("saving photo complete");
        request.setPersonalPhoto(personalPhotoPath);
        request.setIdPhoto(idPhotoPath);
        request.setHighSchoolCertificate(certificatePath);

        return reqRepository.save(request);
    }






}
