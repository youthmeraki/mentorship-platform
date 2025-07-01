package com.youthmeraki.mentorshipplatform.repositories;

import com.youthmeraki.mentorshipplatform.models.AreaOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaOfStudyRepo extends JpaRepository<AreaOfStudy, Long> {
    Optional<AreaOfStudy> findByName(String name);
}