package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class AreaOfStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AreaOfStudy_Mentee> mentees;

    public void addMentee(AreaOfStudy_Mentee mentee) {
        this.mentees.add(mentee);
        if (mentee != null && mentee.getAreaOfStudy() != this) {
            mentee.addAreaOfStudy(this);
        }
    }
    public void removeMentee(AreaOfStudy_Mentee mentee) {
        this.mentees.remove(mentee);
        if (mentee.getAreaOfStudy() == this) {
            mentee.addAreaOfStudy(null);
        }
    }
}
