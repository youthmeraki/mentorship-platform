package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class CountryOfStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CountryOfStudy_Mentee> countryOfStudyMentees;

    public void addMentee(CountryOfStudy_Mentee countryOfStudyMentee) {
        this.countryOfStudyMentees.add(countryOfStudyMentee);
        if (countryOfStudyMentee != null && countryOfStudyMentee.getCountryOfStudy() != this) {
            countryOfStudyMentee.addCountryOfStudy(this);
        }
    }

    public void removeMentee(CountryOfStudy_Mentee countryOfStudyMentee) {
        this.countryOfStudyMentees.remove(countryOfStudyMentee);
        if ( countryOfStudyMentee != null && countryOfStudyMentee.getCountryOfStudy() == this) {
            countryOfStudyMentee.removeCountryOfStudy(this);
        }
    }

}
