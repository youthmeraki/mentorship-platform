package com.youthmeraki.mentorshipplatform.repositories;

import com.youthmeraki.mentorshipplatform.models.InternationalExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternationalExamRepo extends JpaRepository<InternationalExam, Long> {
    Optional<InternationalExam> findByTitle(String title);
}