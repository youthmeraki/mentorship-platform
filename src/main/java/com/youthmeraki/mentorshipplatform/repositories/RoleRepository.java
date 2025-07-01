package com.youthmeraki.mentorshipplatform.repositories;

import com.youthmeraki.mentorshipplatform.models.Role;
import com.youthmeraki.mentorshipplatform.models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType roleType);
}
