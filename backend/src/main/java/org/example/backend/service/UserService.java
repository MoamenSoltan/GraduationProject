package org.example.backend.service;

import org.example.backend.dto.UserDto;
import org.example.backend.entity.User;
import org.example.backend.repository.UserRepository;
import org.example.backend.enums.GenderType;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
