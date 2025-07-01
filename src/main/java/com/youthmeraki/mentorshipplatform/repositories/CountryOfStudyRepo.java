package com.youthmeraki.mentorshipplatform.repositories;

import com.youthmeraki.mentorshipplatform.models.CountryOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryOfStudyRepo extends JpaRepository<CountryOfStudy, Long> {
    Optional<CountryOfStudy> findByName(String name);
}