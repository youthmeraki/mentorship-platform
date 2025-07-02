package com.youthmeraki.mentorshipplatform.services;

import com.youthmeraki.mentorshipplatform.dtos.CreateAdminDto;
import com.youthmeraki.mentorshipplatform.models.Role;
import com.youthmeraki.mentorshipplatform.models.RoleType;
import com.youthmeraki.mentorshipplatform.models.User;
import com.youthmeraki.mentorshipplatform.repositories.RoleRepo;
import com.youthmeraki.mentorshipplatform.repositories.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {


    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);


    public AdminService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }


    @Transactional
    public void approveMentee(String username) {
        User user = getUserByUsername(username);
        if (user.getRole().getName().toString().equals("ROLE_MENTEE")) {
            user.getMentee().setApprove(true);
            userRepo.save(user);
        } else {
            throw new RuntimeException("User is not a mentee");
        }
    }

    public void performAdminTask() {
        // Placeholder for admin task logic
        System.out.println("Performing admin task...");
    }

    @Transactional
    public void createAdmin(CreateAdminDto createAdminDto) {
        Role role = roleRepo.findByName(RoleType.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        User user = User.builder()
                .name(createAdminDto.getName())
                .middleName(createAdminDto.getMiddleName())
                .surname(createAdminDto.getSurname())
                .username(createAdminDto.getUsername())
                .email(createAdminDto.getEmail())
                .phone(createAdminDto.getPhone())
                .password(bCryptPasswordEncoder.encode(createAdminDto.getPassword()))
                .birthdate(createAdminDto.getBirthdate())
                .gender(createAdminDto.isGender())
                .active(createAdminDto.isActive())
                .role(role)
                .build();
        userRepo.save(user);
    }

    @Transactional
    public void deleteUser(String username) {
        User user = getUserByUsername(username);
        userRepo.delete(user);
    }
}
