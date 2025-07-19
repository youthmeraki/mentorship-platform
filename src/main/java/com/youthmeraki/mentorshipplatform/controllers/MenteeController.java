package com.youthmeraki.mentorshipplatform.controllers;

import com.youthmeraki.mentorshipplatform.services.MenteeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/mentee")
@PreAuthorize("principal.isApproved() and principal.isPaid()")
public class MenteeController {

    private final MenteeService menteeService;
    public MenteeController(MenteeService menteeService) {
        this.menteeService = menteeService;
    }


    @GetMapping("/hello")
    public String hello() {
        return "Hello Approved and Paid Mentee!";
    }
}
