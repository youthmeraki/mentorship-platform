package com.youthmeraki.mentorshipplatform.controllers;

import com.youthmeraki.mentorshipplatform.dtos.MenteeDTO;
import com.youthmeraki.mentorshipplatform.services.MenteeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
