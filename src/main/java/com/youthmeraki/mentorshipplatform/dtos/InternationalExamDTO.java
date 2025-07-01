package com.youthmeraki.mentorshipplatform.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InternationalExamDTO {
    private String title;
    private String score;
    private Date date;
}
