package com.youthmeraki.mentorshipplatform.repositories;

import com.youthmeraki.mentorshipplatform.models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import com.youthmeraki.mentorshipplatform.models.Role;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleType name);

}
