package com.youthmeraki.mentorshipplatform.controllers;

import com.youthmeraki.mentorshipplatform.dtos.CreateMenteeDTO;
import com.youthmeraki.mentorshipplatform.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
