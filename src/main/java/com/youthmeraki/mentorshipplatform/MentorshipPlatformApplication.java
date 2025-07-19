package com.youthmeraki.mentorshipplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MentorshipPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentorshipPlatformApplication.class, args);
    }

}
