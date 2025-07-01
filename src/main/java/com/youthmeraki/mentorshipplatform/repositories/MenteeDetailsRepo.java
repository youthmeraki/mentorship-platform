package com.youthmeraki.mentorshipplatform.repositories;

import com.youthmeraki.mentorshipplatform.models.MenteeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeDetailsRepo extends JpaRepository<MenteeDetails, Long> {

}