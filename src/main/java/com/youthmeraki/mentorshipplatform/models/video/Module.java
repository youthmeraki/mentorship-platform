package com.youthmeraki.mentorshipplatform.models.video;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Module {

    @Id
    private long id;

    private String name;
    private String description;

    @OneToMany
    private Set<Video> video;
}
