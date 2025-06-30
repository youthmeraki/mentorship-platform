package com.youthmeraki.mentorshipplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.youthmeraki.mentorshipplatform.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
