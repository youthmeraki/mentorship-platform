package com.youthmeraki.mentorshipplatform.controllers;

import com.youthmeraki.mentorshipplatform.dtos.CreateMenteeDTO;
import com.youthmeraki.mentorshipplatform.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register/mentee")
    public ResponseEntity<?> registerMentee(@RequestBody CreateMenteeDTO createMenteeDTO) {
        userService.registerMentee(createMenteeDTO);
        return ResponseEntity.status(HttpStatus.OK).body("\"Mentee registration initiated. Please check your email for verification instructions.\"");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyMentee(@RequestParam("token") String token) {
        System.out.println("Verifying mentee with token: " + token);
        userService.confirmMenteeRegistration(token);
        return ResponseEntity.status(HttpStatus.CREATED).body("\"Mentee registration successful.\"");
    }

}
