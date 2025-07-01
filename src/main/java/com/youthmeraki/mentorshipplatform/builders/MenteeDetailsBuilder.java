package com.youthmeraki.mentorshipplatform.builders;

import com.youthmeraki.mentorshipplatform.models.AreaOfStudy;
import com.youthmeraki.mentorshipplatform.models.CountryOfStudy;
import com.youthmeraki.mentorshipplatform.models.InternationalExam;
import com.youthmeraki.mentorshipplatform.models.Parent;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class MenteeDetailsBuilder implements IMenteeDetailsBuilder{

    private String nationality;
    private String city;
    private String residingCountry;
    private String residingCity;
    private List<Parent> parents;
    private String highestDegreeLevel;
    private String institutionName;
    private String finalGradeObtained;
    private Date expectedFinishDate;
    private List<AreaOfStudy> areasOfStudy;
    private List<CountryOfStudy> countriesOfStudy;
    private List<InternationalExam> internationalExams;


    @Override
    public void setNationality(String nationality) {
        if (nationality != null && !nationality.isEmpty()) {
            this.nationality = nationality;
        }else {
            throw new IllegalArgumentException("Nationality cannot be null or empty");
        }
    }

    @Override
    public void setCity(String city) {
        if (city != null && !city.isEmpty()) {
            this.city = city;
        } else {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
    }

    @Override
    public void setResidingCountry(String residingCountry) {
        if (residingCountry != null && !residingCountry.isEmpty()) {
            this.residingCountry = residingCountry;
        } else {
            throw new IllegalArgumentException("Residing country cannot be null or empty");
        }
    }

    @Override
    public void setResidingCity(String residingCity) {
        if (residingCity != null && !residingCity.isEmpty()) {
            this.residingCity = residingCity;
        } else {
            throw new IllegalArgumentException("Residing city cannot be null or empty");
        }
    }

    @Override
    public void setParents(List<Parent> parents) {
        if (parents != null && !parents.isEmpty()) {
            this.parents = parents;
        } else {
            throw new IllegalArgumentException("Parents list cannot be null or empty");
        }
    }

    @Override
    public void setHighestDegreeLevel(String highestDegreeLevel) {
        if (highestDegreeLevel != null && !highestDegreeLevel.isEmpty()) {
            this.highestDegreeLevel = highestDegreeLevel;
        } else {
            throw new IllegalArgumentException("Highest degree level cannot be null or empty");
        }
    }

    @Override
    public void setInstitutionName(String institutionName) {
        if (institutionName != null && !institutionName.isEmpty()) {
            this.institutionName = institutionName;
        } else {
            throw new IllegalArgumentException("Institution name cannot be null or empty");
        }
    }

    @Override
    public void setFinalGradeObtained(String finalGradeObtained) {
        if (finalGradeObtained != null && !finalGradeObtained.isEmpty()) {
            this.finalGradeObtained = finalGradeObtained;
        } else {
            throw new IllegalArgumentException("Final grade obtained cannot be null or empty");
        }
    }

    @Override
    public void setExpectedFinishDate(Date expectedFinishDate) {
        if (expectedFinishDate != null) {
            this.expectedFinishDate = expectedFinishDate;
        } else {
            throw new IllegalArgumentException("Expected finish date cannot be null");
        }
    }

    @Override
    public void setAreasOfStudy(List<AreaOfStudy> areasOfStudy) {
        if (areasOfStudy != null && !areasOfStudy.isEmpty()) {
            this.areasOfStudy = areasOfStudy;
        } else {
            throw new IllegalArgumentException("Areas of study list cannot be null or empty");
        }
    }

    @Override
    public void setCountriesOfStudy(List<CountryOfStudy> countriesOfStudy) {
        if (countriesOfStudy != null && !countriesOfStudy.isEmpty()) {
            this.countriesOfStudy = countriesOfStudy;
        } else {
            throw new IllegalArgumentException("Countries of study list cannot be null or empty");
        }
    }

    @Override
    public void setInternationalExams(List<InternationalExam> internationalExams) {
        if (internationalExams != null && !internationalExams.isEmpty()) {
            this.internationalExams = internationalExams;
        } else {
            throw new IllegalArgumentException("International exams list cannot be null or empty");
        }
    }

}
//    public MenteeDetailsRepo(String nationality, String city,
//    String residingCountry, String residingCity, String highestDegreeLevel,
//    String institutionName, String finalGradeObtained, Date expectedFinishDate) {
