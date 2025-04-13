package org.example.backend.mapper;

import org.example.backend.dto.taskDTO.RequestTaskDTO;
import org.example.backend.dto.taskDTO.ResponseTaskDTO;
import org.example.backend.entity.Task;
import org.example.backend.util.FileResponse;

import java.util.List;

public class TaskMapper {
    private FileResponse fileResponse=new FileResponse();
    // Convert RequestTaskDTO to Task entity
    public static Task toEntity(RequestTaskDTO requestTaskDTO) {
        Task task = new Task();
        task.setTaskName(requestTaskDTO.getTaskName());
        task.setMaxGrade(requestTaskDTO.getMaxGrade());
        task.setDescription(requestTaskDTO.getDescription());
//        task.setActive(requestTaskDTO.getIsActive());
        task.setDeadline(requestTaskDTO.getDeadline());

        return task;
    }


    public ResponseTaskDTO toResponseDTO(Task task) {

        ResponseTaskDTO responseTaskDTO = new ResponseTaskDTO();
        responseTaskDTO.setId(task.getId());
        responseTaskDTO.setTaskName(task.getTaskName());
        responseTaskDTO.setMaxGrade(task.getMaxGrade());
        responseTaskDTO.setDescription(task.getDescription());
        responseTaskDTO.setAttachment(fileResponse.getFileName(task.getAttachment()));
        responseTaskDTO.setActive(task.isActive());
        responseTaskDTO.setDeadline(task.getDeadline());
        responseTaskDTO.setCourseId(task.getCourse().getCourseId());
        responseTaskDTO.setCreatedAt(task.getCreatedAt());
        return responseTaskDTO;
    }
}
