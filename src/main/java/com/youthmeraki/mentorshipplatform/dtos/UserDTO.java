package com.youthmeraki.mentorshipplatform.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserDTO {

    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private String middleName;
    @Column(nullable = false)
    private String surname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date birthdate;


    @Column(nullable = false)
    private boolean gender; //male = 1 & female = 0
}
