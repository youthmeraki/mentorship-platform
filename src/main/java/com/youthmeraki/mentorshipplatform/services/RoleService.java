package com.youthmeraki.mentorshipplatform.services;

import com.youthmeraki.mentorshipplatform.models.Role;
import com.youthmeraki.mentorshipplatform.repositories.RoleRepo;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepo roleRepo;
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role getRole(String roleName) {
        return roleRepo.findByName(roleName);
    }

}
