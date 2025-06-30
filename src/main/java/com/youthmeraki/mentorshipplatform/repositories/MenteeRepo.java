package com.youthmeraki.mentorshipplatform.repositories;

import com.youthmeraki.mentorshipplatform.models.Mentee;
import com.youthmeraki.mentorshipplatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenteeRepo extends JpaRepository<Mentee, Long> {
    boolean existsByUser(User user);
}
