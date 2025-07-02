package com.youthmeraki.mentorshipplatform.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminDto {
    private String username;
    private String name;
    private String middleName;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private Date birthdate;
    private boolean gender;
    private boolean isActive = true;
}
