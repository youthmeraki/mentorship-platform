package com.youthmeraki.mentorshipplatform.builders;

import com.youthmeraki.mentorshipplatform.models.AreaOfStudy;
import com.youthmeraki.mentorshipplatform.models.CountryOfStudy;
import com.youthmeraki.mentorshipplatform.models.InternationalExam;
import com.youthmeraki.mentorshipplatform.models.Parent;

import java.util.Date;
import java.util.List;

public interface IMenteeDetailsBuilder {

    void setNationality(String nationality);
    void setCity(String city);
    void setResidingCountry(String residingCountry);
    void setResidingCity(String residingCity);
    void setParents(List<Parent> parents);

    void setHighestDegreeLevel(String highestDegreeLevel);
    void setInstitutionName(String institutionName);
    void setFinalGradeObtained(String finalGradeObtained);
    void setExpectedFinishDate(Date expectedFinishDate);

    void setAreasOfStudy(List<AreaOfStudy> areasOfStudy);
    void setCountriesOfStudy(List<CountryOfStudy> countriesOfStudy);
    void setInternationalExams(List<InternationalExam> internationalExams);

}
