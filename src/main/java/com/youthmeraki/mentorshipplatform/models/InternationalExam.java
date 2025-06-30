package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class InternationalExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private double average;

    @OneToMany(mappedBy = "internationalExam")
    private List<InternationalExam_Mentee> internationalExamMentees;

    public void addInternationalExamMentee(InternationalExam_Mentee ie) {
        this.internationalExamMentees.add(ie);
        if ( ie != null && ie.getInternationalExam() != this) {
            ie.addInternationalExam(this);
        }
    }

    public void removeInternationalExamMentee(InternationalExam_Mentee ie) {
        this.internationalExamMentees.remove(ie);
        if (ie.getInternationalExam() == this) {
            ie.addInternationalExam(null);
        }
    }
}
