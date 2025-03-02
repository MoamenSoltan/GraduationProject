package org.example.backend.service;

import org.example.backend.dto.UserDto;
import org.example.backend.entity.Role;
import org.example.backend.entity.SubmissionRequest;
import org.example.backend.entity.User;
import org.example.backend.enums.AdmissionStatus;
import org.example.backend.mapper.SubmissionRequestMapper;
import org.example.backend.repository.RoleRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.enums.GenderType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SubmissionRequestMapper submissionRequestMapper;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, SubmissionRequestMapper submissionRequestMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.submissionRequestMapper = submissionRequestMapper;
        this.roleRepository = roleRepository;
    }

    public User getEmail(String email)
    {
         return userRepository.findUsersByEmail(email);
    }

    public User save(UserDto userDto)
    {
        User user =new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(GenderType.MALE);
        user.setFirstName("said");
        user.setLastName("sabry");
        return userRepository.save(user);

    }

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public User acceptSubmissionRequest(SubmissionRequest request) {
        // Update the admission status to ACCEPTED
        request.setAdmissionStatus(AdmissionStatus.ACCEPTED);

        // Fetch the Student Role
        Role role = roleRepository.getStudentRole();
        System.out.println("Role is: " + role.toString());

        // Map SubmissionRequest to User
        User user = submissionRequestMapper.mapToUser(request);

        // Set User's Role List
        user.setRoleList(List.of(role));

        // Set the User list in Role (Bidirectional Relationship)
        if (role.getUsers() == null) {
            role.setUsers(List.of(user));
        } else {
            role.getUsers().add(user);
        }

        // Save User First (Cascade Persist will handle join table entry)
        User savedUser = userRepository.save(user);

        // Optional: Save Role explicitly (only if cascade not working)
        roleRepository.save(role);

        return savedUser;
    }



}
