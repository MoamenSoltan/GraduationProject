package org.example.backend.service;

import org.example.backend.dto.submissionDto.SubmissionImages;
import org.example.backend.dto.submissionDto.SubmissionInfoRequestDTO;
import org.example.backend.dto.submissionDto.SubmissionRequestDto;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.mapper.SubmissionRequestMapper;
import org.example.backend.repository.SubmissionReqRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class SubmissionRequestService {
    private final SubmissionReqRepository reqRepository;
    private final SubmissionRequestMapper submissionRequestMapper;
    public final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    private final SubmissionReqRepository submissionReqRepository;

    public SubmissionRequestService(SubmissionReqRepository reqRepository, SubmissionRequestMapper submissionRequestMapper, FileService fileService, PasswordEncoder passwordEncoder, SubmissionReqRepository submissionReqRepository) {
        this.reqRepository = reqRepository;
        this.submissionRequestMapper = submissionRequestMapper;
        this.fileService = fileService;
        this.passwordEncoder = passwordEncoder;
        this.submissionReqRepository = submissionReqRepository;
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


    public String save(SubmissionInfoRequestDTO infoRequestDTO, SubmissionImages images) throws IOException {
        infoRequestDTO.setPassword(passwordEncoder.encode(infoRequestDTO.getPassword()));
        SubmissionRequest request = SubmissionRequestMapper.toEntity(infoRequestDTO);

        String idPhotoPath=fileService.uploadFile(images.getIdPhoto());
        String personalPhotoPath = fileService.uploadFile(images.getPersonalPhoto());
        String certificatePath = fileService.uploadFile(images.getHighSchoolCertificate());

        request.setPersonalPhoto(personalPhotoPath);
        request.setIdPhoto(idPhotoPath);
        request.setHighSchoolCertificate(certificatePath);

        submissionReqRepository.save(request);

        return "submission saved ";

    }




}
