package org.example.backend.service;

import org.example.backend.entity.SubmissionRequest;
import org.example.backend.repository.SubmissionReqRepository;
import org.springframework.stereotype.Service;

@Service
public class SubmissionRequestService {
    private final SubmissionReqRepository reqRepository;

    public SubmissionRequestService(SubmissionReqRepository reqRepository) {
        this.reqRepository = reqRepository;
    }

    public SubmissionRequest saveSubmissionRequest(SubmissionRequest request) {
       return reqRepository.save(request);
    }
}
