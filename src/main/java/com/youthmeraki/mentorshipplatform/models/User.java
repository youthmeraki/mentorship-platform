package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity(name = "Users")
@Getter
@Setter
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Mentee mentee;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Mentor mentor;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;

    public void addRole(Role role) {
        this.roles.add(role);
    }



}
