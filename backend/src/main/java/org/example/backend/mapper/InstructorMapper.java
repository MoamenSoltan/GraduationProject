package org.example.backend.mapper;

import org.example.backend.dto.InstructorDto;
import org.example.backend.entity.Instructor;
import org.example.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class InstructorMapper {

    public Instructor mapperToInstructor(InstructorDto instructorDto)
    {
        User user = new User();
        user.setEmail(instructorDto.getEmail());
        user.setFirstName(instructorDto.getFirstName());
        user.setLastName(instructorDto.getLastName());
        user.setGender(instructorDto.getGender());
        user.setPassword(instructorDto.getPassword());

        Instructor instructor = new Instructor();

        instructor.setUser(user);


        return instructor;
    }
}
