package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "Users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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
    @Column(nullable = true)
    private boolean active;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Mentee mentee;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Mentor mentor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;




}
