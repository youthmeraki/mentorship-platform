package com.youthmeraki.mentorshipplatform.services;

import com.youthmeraki.mentorshipplatform.dtos.CreateUserDTO;
import com.youthmeraki.mentorshipplatform.dtos.UserDTO;
import com.youthmeraki.mentorshipplatform.models.User;
import com.youthmeraki.mentorshipplatform.repositories.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private final UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Transactional
    public  UserDTO createUserWithRoleMentee(CreateUserDTO createUserDTO) {
        createUserDTO.setPassword(bCryptPasswordEncoder.encode(createUserDTO.getPassword()));
        User user = mapToUser(createUserDTO);
        user =  userRepo.save(user);
        return mapToUserDTO(user);
    }


    public User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setBirthdate(userDTO.getBirthdate());
        user.setGender(userDTO.isGender());
        user.setPhone(userDTO.getPhone());
        return user;
    }


    public User mapToUser(CreateUserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setBirthdate(userDTO.getBirthdate());
        user.setGender(userDTO.isGender());
        user.setPhone(userDTO.getPhone());
        return user;
    }

    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setBirthdate(user.getBirthdate());
        userDTO.setGender(user.isGender());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

}
