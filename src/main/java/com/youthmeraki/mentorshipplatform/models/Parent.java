package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fatherName;
    private String fatherPhone;
    private String fatherEmail;

    private String motherName;
    private String motherPhone;
    private String motherEmail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_details_id")
    private MenteeDetails menteeDetails;

}
