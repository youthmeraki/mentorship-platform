package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenteeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nationality;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String residingCountry;
    @Column(nullable = false)
    private String residingCity;
    @Column(nullable = false)
    private String highestDegreeLevel;
    @Column(nullable = false)
    private String institutionName;
    @Column(nullable = false)
    private String finalGradeObtained;
    @Column(nullable = false)
    private Date expectedFinishDate;

    @Column(nullable = false)
    private String degreePursing;

    @OneToOne(mappedBy = "menteeDetails")
    private Mentee mentee;

    @OneToOne(mappedBy = "menteeDetails", cascade = CascadeType.ALL)
    private Parent parent;

    @ManyToMany
    @JoinTable(
            name = "mentee_details_area_of_study",
            joinColumns = @JoinColumn(name = "mentee_details_id"),
            inverseJoinColumns = @JoinColumn(name = "area_of_study_id")
    )
    private Set<AreaOfStudy> areaOfStudies = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "mentee_details_country_of_study",
            joinColumns = @JoinColumn(name = "mentee_details_id"),
            inverseJoinColumns = @JoinColumn(name = "country_of_study_id")
    )
    private Set<CountryOfStudy> countriesOfStudy = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "mentee_details_international_exam",
            joinColumns = @JoinColumn(name = "mentee_details_id"),
            inverseJoinColumns = @JoinColumn(name = "international_exam_id")
    )
    private Set<InternationalExam> internationalExams = new HashSet<>();

}
