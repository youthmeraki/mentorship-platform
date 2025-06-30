package com.youthmeraki.mentorshipplatform.models;

import com.youthmeraki.mentorshipplatform.repositories.RoleRepo;
import com.youthmeraki.mentorshipplatform.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepo repo;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void testCreateRoles() {
        Role admin = new Role("ROLE_ADMIN");
        Role editor = new Role("ROLE_MENTOR");
        Role customer = new Role("ROLE_MENTEE");

        repo.saveAll(List.of(admin, editor, customer));

        long count = repo.count();
        assertEquals(3, count);
    }

    @Test
    public void testAssignRoleToUser() {
        long userId = 3;
        String roleName = "ROLE_ADMIN";
        User user = userRepo.findById(userId).get();
        user.addRole(new Role(roleName));

        User updatedUser = userRepo.save(user);
        assertThat(updatedUser.getRoles()).hasSize(1);

    }
}