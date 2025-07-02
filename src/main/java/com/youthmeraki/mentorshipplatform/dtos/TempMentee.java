package com.youthmeraki.mentorshipplatform.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TempMentee {
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

    private String verificationToken;
    private long verificationTokenExpiry;

    public CreateMenteeDTO toCreateMenteeDTO() {
        return CreateMenteeDTO.builder()
                .username(username)
                .name(name)
                .middleName(middleName)
                .surname(surname)
                .email(email)
                .phone(phone)
                .password(password)
                .gender(gender)
                .subscription(subscription)
                .birthdate(birthdate)

                .nationality(nationality)
                .city(city)
                .residingCountry(residingCountry)
                .residingCity(residingCity)
                .highestDegreeLevel(highestDegreeLevel)
                .institutionName(institutionName)
                .finalGradeObtained(finalGradeObtained)
                .expectedFinishDate(expectedFinishDate)
                .degreePursing(degreePursing)

                .parentDto(parentDto)
                .studyAreas(studyAreas)
                .countriesOfStudy(countriesOfStudy)
                .internationalExams(internationalExams)
                .build();
    }

    @JsonIgnore
    public boolean isExpired() {
        return Instant.now().toEpochMilli() > verificationTokenExpiry;
    }

}
