package org.example.backend.mapper;

import org.example.backend.dto.taskDTO.ResponseTaskSubmissionDTO;
import org.example.backend.dto.taskDTO.TaskSubmissionDTO;
import org.example.backend.entity.TaskSubmission;
import org.example.backend.util.FileResponse;

public class TaskSubmissionMapper {
    private FileResponse fileResponse = new FileResponse();
    public static TaskSubmission toEntity(TaskSubmissionDTO taskSubmissionDTO) {
        TaskSubmission taskSubmission = new TaskSubmission();
        return taskSubmission;
    }

    public ResponseTaskSubmissionDTO toResponseDTO(TaskSubmission taskSubmission)
    {
        ResponseTaskSubmissionDTO responseTaskSubmissionDTO = new ResponseTaskSubmissionDTO();
        responseTaskSubmissionDTO.setId(taskSubmission.getId());
        responseTaskSubmissionDTO.setAttachment(fileResponse.getFileName(taskSubmission.getAttachment()));
        responseTaskSubmissionDTO.setSubmittedAt(taskSubmission.getSubmittedAt().toString());
        responseTaskSubmissionDTO.setGrade(taskSubmission.getGrade());
        responseTaskSubmissionDTO.setTaskId(taskSubmission.getTask().getId());
        responseTaskSubmissionDTO.setStudentEmail(taskSubmission.getStudent().getUser().getEmail());
        return responseTaskSubmissionDTO;
    }
}
