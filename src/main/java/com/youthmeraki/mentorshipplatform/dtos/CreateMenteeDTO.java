package com.youthmeraki.mentorshipplatform.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
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
    private List<StudyAreaDTO> studyAreas;
    private List<CountryOfStudyDTO> countriesOfStudy;
    private List<InternationalExamDTO> internationalExams;

}
