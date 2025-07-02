package com.youthmeraki.mentorshipplatform.services;

import com.youthmeraki.mentorshipplatform.dtos.CreateAdminDto;
import com.youthmeraki.mentorshipplatform.models.RoleType;
import com.youthmeraki.mentorshipplatform.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    void createAdmin() {
        Date birthdate = Date.from(LocalDate.of(2003, 8, 16)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        CreateAdminDto createAdminDto = new CreateAdminDto();
        createAdminDto.setUsername("ivanmanhique");
        createAdminDto.setName("Ivan");
        createAdminDto.setSurname("Manhique");
        createAdminDto.setMiddleName("V.M");
        createAdminDto.setEmail("ivan.manhique@youthmeraki.com");
        createAdminDto.setPhone("+48518454430");
        createAdminDto.setPassword("ratjyt-cyccE8-sekqup");
        createAdminDto.setBirthdate(birthdate);
        createAdminDto.setGender(true);
        createAdminDto.setActive(true);
        adminService.createAdmin(createAdminDto);

        User createdUser = adminService.getUserByUsername("ivanmanhique");
        assertNotNull(createdUser);
        assertEquals("ivanmanhique", createdUser.getUsername());
        assertEquals(RoleType.ROLE_ADMIN, createdUser.getRole().getName());
    }
}