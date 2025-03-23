package org.example.backend.service;

import org.example.backend.dto.submissionDto.SubmissionImages;
import org.example.backend.dto.submissionDto.SubmissionInfoRequestDTO;
import org.example.backend.dto.submissionDto.SubmissionRequestDto;
import org.example.backend.dto.submissionDto.SubmissionResponseDTO;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.mapper.SubmissionRequestMapper;
import org.example.backend.repository.SubmissionReqRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<SubmissionResponseDTO> getAllSubmissions(AdmissionStatus status)
    {

        List<SubmissionRequest> submissionRequests = submissionReqRepository.getSubmissionRequestByStatus(status);
        List<SubmissionResponseDTO> responseDTOList = new ArrayList<>();

        for (SubmissionRequest request : submissionRequests) {
            responseDTOList.add(submissionRequestMapper.toResponseDTO(request));
        }

        return responseDTOList;

    }


    public SubmissionResponseDTO getSubmissionById(int id) {
        SubmissionRequest request = submissionReqRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Submission","submission ID",id));
        return submissionRequestMapper.toResponseDTO(request);
    }

    public List<SubmissionResponseDTO> getSubmissionsWithSorting(String field)
    {
        List<SubmissionRequest> submissionRequests = submissionReqRepository.findAll(Sort.by(Sort.Direction.ASC,field));
        List<SubmissionResponseDTO> responseDTOList = new ArrayList<>();

        for (SubmissionRequest request : submissionRequests) {
            responseDTOList.add(submissionRequestMapper.toResponseDTO(request));
        }

        return responseDTOList;
    }

    public List<SubmissionRequest> getSubmissionWithPagination(int offset, int pageSize)
    {
        Page<SubmissionRequest> submissionRequests=
                submissionReqRepository.findAll(PageRequest.of(offset,pageSize));
        return submissionRequests.getContent();
    }

    public List<SubmissionResponseDTO> getAllSubmissions(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<SubmissionRequest> submissionRequests = submissionReqRepository.findAll(pageable);

        return submissionRequests.getContent()
                .stream()
                .map(submissionRequestMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
