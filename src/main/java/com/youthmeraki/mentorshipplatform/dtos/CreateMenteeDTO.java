package com.youthmeraki.mentorshipplatform.dtos;

import com.youthmeraki.mentorshipplatform.models.Subscription;
import com.youthmeraki.mentorshipplatform.models.User;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMenteeDTO {

    @Column(nullable = false)
    private String subscription;

}
