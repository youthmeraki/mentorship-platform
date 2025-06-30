package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
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

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL)
    private List<Parent> parents;

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL)
    private List<AreaOfStudy_Mentee> areasOfStudy;

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL)
    private List<CountryOfStudy_Mentee> countriesOfStudy;

    @OneToMany(mappedBy = "mentee",cascade = CascadeType.ALL)
    private List<InternationalExam_Mentee> internationalExams;

    public MenteeDetails() {

    }

    public MenteeDetails(String nationality, String city, String residingCountry, String residingCity, String highestDegreeLevel, String institutionName, String finalGradeObtained, Date expectedFinishDate) {
        this.nationality = nationality;
        this.city = city;
        this.residingCountry = residingCountry;
        this.residingCity = residingCity;
        this.highestDegreeLevel = highestDegreeLevel;
        this.institutionName = institutionName;
        this.finalGradeObtained = finalGradeObtained;
        this.expectedFinishDate = expectedFinishDate;
    }

    public void addParent(Parent parent) {
        parents.add(parent);
        if (parent.getMentee() != this) {
            parent.setMentee(this);
        }
    }

    public void removeParent(Parent parent) {
        parents.remove(parent);
        if (parent.getMentee() != this) {
            parent.removeMentee(this);
        }
    }

    public void addAreasOfStudy(AreaOfStudy_Mentee areaOfStudyMentee) {
        areasOfStudy.add(areaOfStudyMentee);
        if (areaOfStudyMentee.getMentee() != this){
            areaOfStudyMentee.addMentee(null);
        }
    }
    public void removeAreasOfStudy(AreaOfStudy_Mentee areaOfStudyMentee) {
        areasOfStudy.remove(areaOfStudyMentee);
        if (areaOfStudyMentee.getMentee() != this){
            areaOfStudyMentee.removeMentee(this);
        }
    }

    public void addCountriesOfStudy(CountryOfStudy_Mentee countryOfStudyMentee) {
        countriesOfStudy.add(countryOfStudyMentee);
        if (countryOfStudyMentee.getMentee() != this){
            countryOfStudyMentee.addMentee(null);
        }
    }
    public void removeCountryOfStudy(CountryOfStudy_Mentee countryOfStudyMentee) {
        countriesOfStudy.remove(countryOfStudyMentee);
        if (countryOfStudyMentee.getMentee() == this){
            countryOfStudyMentee.removeMentee(this);
        }
    }

    public void addInternationalExams(InternationalExam_Mentee internationalExam) {
        internationalExams.add(internationalExam);
        if (internationalExam.getMentee() != this){
            internationalExam.addMentee(this);
        }
    }
    public void removeInternationalExams(InternationalExam_Mentee internationalExam) {
        internationalExams.remove(internationalExam);
        if (internationalExam.getMentee() == this){
            internationalExam.removeMentee(this);
        }
    }

}
