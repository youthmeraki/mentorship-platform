package com.youthmeraki.mentorshipplatform.services;

import com.youthmeraki.mentorshipplatform.dtos.CreateMenteeDTO;
import com.youthmeraki.mentorshipplatform.models.Role;
import com.youthmeraki.mentorshipplatform.models.RoleType;
import com.youthmeraki.mentorshipplatform.models.User;
import com.youthmeraki.mentorshipplatform.repositories.RoleRepository;
import com.youthmeraki.mentorshipplatform.repositories.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final MenteeService menteeService;
    private final MentorService mentorService;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepository, MenteeService menteeService, MentorService mentorService) {
        this.userRepo = userRepo;
        this.roleRepository = roleRepository;
        this.menteeService = menteeService;
        this.mentorService = mentorService;
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Transactional
    public void registerMentee(CreateMenteeDTO createMenteeDTO) {

        Role menteeRole = roleRepository.findByName(RoleType.ROLE_MENTEE)
                .orElseThrow(() -> new RuntimeException("Mentee role not found"));

        User user = User.builder()
                .username(createMenteeDTO.getUsername())
                .name(createMenteeDTO.getName())
                .middleName(createMenteeDTO.getMiddleName())
                .surname(createMenteeDTO.getSurname())
                .email(createMenteeDTO.getEmail())
                .phone(createMenteeDTO.getPhone())
                .birthdate(createMenteeDTO.getBirthdate())
                .password(bCryptPasswordEncoder.encode(createMenteeDTO.getPassword()))
                .role(menteeRole)
                .build();
        userRepo.save(user);

        menteeService.registerMentee(createMenteeDTO, user);
    }

//    @Transactional
//    public  UserDTO createUserWithRoleMentee(CreateUserDTO createUserDTO) {
//        createUserDTO.setPassword(bCryptPasswordEncoder.encode(createUserDTO.getPassword()));
//        User user = mapToUser(createUserDTO);
//        user =  userRepo.save(user);
//        return mapToUserDTO(user);
//    }

}
