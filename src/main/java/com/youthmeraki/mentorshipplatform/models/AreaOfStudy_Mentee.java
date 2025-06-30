package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AreaOfStudy_Mentee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private AreaOfStudy areaOfStudy;

    @ManyToOne(cascade = CascadeType.ALL)
    private MenteeDetails mentee;

    public void addAreaOfStudy(AreaOfStudy areaOfStudy) {
        this.areaOfStudy = areaOfStudy;
        if (areaOfStudy != null &&  !areaOfStudy.getMentees().contains(this)) {
            areaOfStudy.addMentee(this);
        }
    }
    public void addMentee(MenteeDetails mentee) {
        this.mentee = mentee;
        if (mentee != null && !mentee.getAreasOfStudy().contains(this)) {
            mentee.addAreasOfStudy(this);
        }
    }

    public void removeAreaOfStudy(AreaOfStudy areaOfStudy) {
        areaOfStudy.removeMentee(this);
        if (areaOfStudy.getMentees().contains(this)) {
            areaOfStudy.addMentee(null);
        }
    }

    public void removeMentee(MenteeDetails mentee) {
        mentee.removeAreasOfStudy(this);
        if (mentee.getAreasOfStudy().contains(this)) {
            mentee.removeAreasOfStudy(this);
        }
    }
}
