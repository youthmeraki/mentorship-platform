package com.youthmeraki.mentorshipplatform.controllers;

import com.youthmeraki.mentorshipplatform.dtos.UserLoginDTO;
import com.youthmeraki.mentorshipplatform.services.AuthService;
import com.youthmeraki.mentorshipplatform.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

//    @PostMapping("register-user")
//    public ResponseEntity<UserDTO> registerUser(@RequestBody CreateUserDTO createUserDTO) {
//        UserDTO userDTO = userService.createUserWithRoleMentee(createUserDTO);
//        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
//    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(authService.authenticate(userLoginDTO), HttpStatus.OK);
    }
}
