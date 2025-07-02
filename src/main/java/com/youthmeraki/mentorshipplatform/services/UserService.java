package com.youthmeraki.mentorshipplatform.services;

import com.youthmeraki.mentorshipplatform.dtos.CreateMenteeDTO;
import com.youthmeraki.mentorshipplatform.dtos.TempMentee;
import com.youthmeraki.mentorshipplatform.exceptions.EmailAlreadyExistsException;
import com.youthmeraki.mentorshipplatform.exceptions.TokenExpiredException;
import com.youthmeraki.mentorshipplatform.exceptions.UsernameAlreadyExistsException;
import com.youthmeraki.mentorshipplatform.models.Role;
import com.youthmeraki.mentorshipplatform.models.RoleType;
import com.youthmeraki.mentorshipplatform.models.User;
import com.youthmeraki.mentorshipplatform.repositories.RoleRepository;
import com.youthmeraki.mentorshipplatform.repositories.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final MenteeService menteeService;
    private final MentorService mentorService;
    private final ValidationService validationService;
    private final TwoStepVerificationService twoStepVerificationService;

    public UserService(UserRepo userRepo, RoleRepository roleRepository, MenteeService menteeService, MentorService mentorService, ValidationService validationService, TwoStepVerificationService twoStepVerificationService) {
        this.userRepo = userRepo;
        this.roleRepository = roleRepository;
        this.menteeService = menteeService;
        this.mentorService = mentorService;
        this.validationService = validationService;
        this.twoStepVerificationService = twoStepVerificationService;
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Transactional
    public void registerMentee(CreateMenteeDTO createMenteeDTO) {

        validationService.validateMenteeRegistration(createMenteeDTO);
        if (userRepo.existsUserByUsername(createMenteeDTO.getUsername())) {
            throw new UsernameAlreadyExistsException(createMenteeDTO.getUsername());
        }
        if (userRepo.existsUserByEmail((createMenteeDTO.getEmail()))){
            throw new EmailAlreadyExistsException("Email already registered");
        }

        TempMentee tempMentee =  twoStepVerificationService.createTempMentee(createMenteeDTO);
        twoStepVerificationService.save(tempMentee);

        twoStepVerificationService.sendVerificationEmail(createMenteeDTO.getEmail(), tempMentee.getVerificationToken());
    }

    @Transactional
    public void confirmMenteeRegistration(String token) {
        TempMentee tempMentee = twoStepVerificationService.get(token);

        if ( tempMentee == null || tempMentee.isExpired()) {
            twoStepVerificationService.delete(token);
            throw new TokenExpiredException("Token is expired");
        }
        Role menteeRole = roleRepository.findByName(RoleType.ROLE_MENTEE)
                .orElseThrow(() -> new RuntimeException("Mentee role not found"));

        CreateMenteeDTO createMenteeDTO = tempMentee.toCreateMenteeDTO();

        User user = User.builder()
                .username(createMenteeDTO.getUsername())
                .name(createMenteeDTO.getName())
                .middleName(createMenteeDTO.getMiddleName())
                .surname(createMenteeDTO.getSurname())
                .email(createMenteeDTO.getEmail())
                .phone(createMenteeDTO.getPhone())
                .birthdate(createMenteeDTO.getBirthdate())
                .password(createMenteeDTO.getPassword())
                .role(menteeRole)
                .build();
        userRepo.save(user);

        menteeService.registerMentee(createMenteeDTO, user);

        twoStepVerificationService.delete(token);
    }
}
