package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CountryOfStudy_Mentee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private CountryOfStudy countryOfStudy;

    @ManyToOne(cascade = CascadeType.ALL)
    private MenteeDetails mentee;

    public void addCountryOfStudy(CountryOfStudy countryOfStudy) {
        this.countryOfStudy = countryOfStudy;
        if (countryOfStudy != null && !countryOfStudy.getCountryOfStudyMentees().contains(this)) {
            countryOfStudy.addMentee(this);
        }
    }

    public void removeCountryOfStudy(CountryOfStudy countryOfStudy) {
        this.countryOfStudy = null;
        if (countryOfStudy.getCountryOfStudyMentees().contains(this)) {
            countryOfStudy.removeMentee(this);
        }
    }

    public void addMentee(MenteeDetails mentee) {
        this.mentee = mentee;
        if (mentee != null && !mentee.getCountriesOfStudy().contains(this)) {
            mentee.addCountriesOfStudy(this);
        }
    }

    public void removeMentee(MenteeDetails mentee) {
        this.mentee = null;
        if (mentee.getCountriesOfStudy().contains(this)) {
            mentee.removeCountryOfStudy(this);
        }
    }
}
