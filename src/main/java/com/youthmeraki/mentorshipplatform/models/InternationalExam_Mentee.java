package com.youthmeraki.mentorshipplatform.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class InternationalExam_Mentee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private InternationalExam internationalExam;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenteeDetails mentee;

    public void addInternationalExam(InternationalExam internationalExam) {
        this.internationalExam = internationalExam;
        if (internationalExam != null && !internationalExam.getInternationalExamMentees().contains(this)) {
            internationalExam.addInternationalExamMentee(this);
        }
    }

    public void removeInternationalExam(InternationalExam internationalExam) {
        this.internationalExam = null;
        internationalExam.getInternationalExamMentees().remove(this);
    }

    public void addMentee(MenteeDetails mentee) {
        this.mentee = mentee;
        if (!mentee.getInternationalExams().contains(this)) {
            mentee.getInternationalExams().add(this);
        }
    }

    public void removeMentee(MenteeDetails mentee) {
        this.mentee = null;
        mentee.getInternationalExams().remove(this);
    }

}
