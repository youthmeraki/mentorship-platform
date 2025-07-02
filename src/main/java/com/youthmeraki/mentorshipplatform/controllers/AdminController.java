package com.youthmeraki.mentorshipplatform.controllers;

import com.youthmeraki.mentorshipplatform.dtos.CreateAdminDto;
import com.youthmeraki.mentorshipplatform.models.User;
import com.youthmeraki.mentorshipplatform.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String adminDashboard() {
        return "Welcome to the Admin Dashboard!";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PatchMapping("/approve-mentee")
    public ResponseEntity<String> approveMentee(String username) {
        adminService.approveMentee(username);
        return ResponseEntity.ok("Mentee approved successfully");
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(@RequestBody CreateAdminDto createAdminDto) {
        adminService.createAdmin(createAdminDto);
        return ResponseEntity.ok("Admin created successfully");
    }

}
