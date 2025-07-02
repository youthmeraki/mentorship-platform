package com.youthmeraki.mentorshipplatform.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenteeDTO {

    // Personal Information
    private String username;
    private String name;
    private String middleName;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private boolean gender;
    private String subscription;
    private Date birthdate;

    // MenteeDetailsRepo Information
    private String nationality;
    private String city;
    private String residingCountry;
    private String residingCity;
    private String highestDegreeLevel;
    private String institutionName;
    private String finalGradeObtained;
    private Date expectedFinishDate;
    private String degreePursing;


    private ParentDTO parentDto;
    @Builder.Default
    private List<StudyAreaDTO> studyAreas = new ArrayList<>();;
    @Builder.Default
    private List<CountryOfStudyDTO> countriesOfStudy = new ArrayList<>();;
    @Builder.Default
    private List<InternationalExamDTO> internationalExams = new ArrayList<>();;

    @Override
    public String toString() {
        return "CreateMenteeDTO{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", subscription='" + subscription + '\'' +
                ", birthdate=" + birthdate +
                ", nationality='" + nationality + '\'' +
                ", city='" + city + '\'' +
                ", residingCountry='" + residingCountry + '\'' +
                ", residingCity='" + residingCity + '\'' +
                ", highestDegreeLevel='" + highestDegreeLevel + '\'' +
                ", institutionName='" + institutionName + '\'' +
                ", finalGradeObtained='" + finalGradeObtained + '\'' +
                ", expectedFinishDate=" + expectedFinishDate +
                ", degreePursing='" + degreePursing + '\'' +
                ", parentDto=" + parentDto +
                ", studyAreas=" + studyAreas +
                ", countriesOfStudy=" + countriesOfStudy +
                ", internationalExams=" + internationalExams +
                '}';
    }
}
