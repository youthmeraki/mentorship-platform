package com.youthmeraki.mentorshipplatform.controllers;

import com.youthmeraki.mentorshipplatform.dtos.CreateMenteeDTO;
import com.youthmeraki.mentorshipplatform.dtos.MenteeDTO;
import com.youthmeraki.mentorshipplatform.services.MenteeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mentee")
public class MenteeController {

    private final MenteeService menteeService;
    public MenteeController(MenteeService menteeService) {
        this.menteeService = menteeService;
    }

    @GetMapping
    public ResponseEntity<List<MenteeDTO>> getAllMentees() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Mentee!";
    }

    @PostMapping("/create-mentee")
    public ResponseEntity<?> createMentee(@RequestBody CreateMenteeDTO mentee, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.split(" ")[1];
        MenteeDTO menteeDTO = menteeService.createMentee(mentee, token);
        return ResponseEntity.ok(menteeDTO);
    }
}
