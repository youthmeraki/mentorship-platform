package com.youthmeraki.mentorshipplatform.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenteeDetails mentee;

    public void addMentee(MenteeDetails mentee) {
        this.mentee = mentee;
        if (!mentee.getParents().contains(this)) {
            mentee.addParent(this);
        }
    }
    public void removeMentee(MenteeDetails mentee) {
        this.mentee = null;
        if (mentee.getParents().contains(this)) {
            mentee.removeParent(this);
        }
    }
}
