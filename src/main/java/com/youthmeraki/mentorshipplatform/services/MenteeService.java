package com.youthmeraki.mentorshipplatform.services;

import com.youthmeraki.mentorshipplatform.dtos.CreateMenteeDTO;
import com.youthmeraki.mentorshipplatform.dtos.MenteeDTO;
import com.youthmeraki.mentorshipplatform.models.*;
import com.youthmeraki.mentorshipplatform.repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenteeService {

    private final MenteeRepo menteeRepository;
    private final MenteeDetailsRepo menteeDetailsRepository;
    private final ParentRepo parentRepository;
    private final AreaOfStudyRepo areaOfStudyRepository;
    private final CountryOfStudyRepo countryOfStudyRepository;
    private final InternationalExamRepo internationalExamRepository;


    public MenteeService(MenteeRepo menteeRepository, MenteeDetailsRepo menteeDetailsRepository, ParentRepo parentRepository, AreaOfStudyRepo areaOfStudyRepository, CountryOfStudyRepo countryOfStudyRepository, InternationalExamRepo internationalExamRepository) {
        this.menteeRepository = menteeRepository;
        this.menteeDetailsRepository = menteeDetailsRepository;
        this.parentRepository = parentRepository;
        this.areaOfStudyRepository = areaOfStudyRepository;
        this.countryOfStudyRepository = countryOfStudyRepository;
        this.internationalExamRepository = internationalExamRepository;
    }

    public List<Mentee> getAllMentees() {
        return menteeRepository.findAll();
    }

    @Transactional
    public void registerMentee(CreateMenteeDTO createMenteeDTO, User user) {

        Parent parent = new Parent();
        BeanUtils.copyProperties(createMenteeDTO.getParentDto(), parent);
        parentRepository.save(parent);


        Subscription subscription = Subscription.fromString(createMenteeDTO.getSubscription());

        Mentee mentee = Mentee.builder()
                .user(user)
                .subscription(subscription)
                .isPaid(false)
                .build();

        Set<AreaOfStudy> studyAreas = createMenteeDTO.getStudyAreas().stream()
                .map(studyAreaDTO -> areaOfStudyRepository.findByName(studyAreaDTO.getName())
                        .orElseThrow(() -> new RuntimeException("Area of study not found: " + studyAreaDTO.getName())))
                .collect(Collectors.toSet());

        Set<CountryOfStudy> countries = createMenteeDTO.getCountriesOfStudy().stream()
                .map(countryDTO -> countryOfStudyRepository.findByName(countryDTO.getName())
                        .orElseThrow(() -> new RuntimeException("Country of study not found: " + countryDTO.getName())))
                .collect(Collectors.toSet());

        Set<InternationalExam> exams = createMenteeDTO.getInternationalExams().stream()
                .map(examDTO -> internationalExamRepository.findByTitle(examDTO.getTitle())
                        .orElseThrow(() -> new RuntimeException("International exam not found: " + examDTO.getTitle())))
                .collect(Collectors.toSet());

        MenteeDetails menteeDetails = MenteeDetails.builder()
                .nationality(createMenteeDTO.getNationality())
                .city(createMenteeDTO.getCity())
                .residingCountry(createMenteeDTO.getResidingCountry())
                .residingCity(createMenteeDTO.getResidingCity())
                .highestDegreeLevel(createMenteeDTO.getHighestDegreeLevel())
                .institutionName(createMenteeDTO.getInstitutionName())
                .finalGradeObtained(createMenteeDTO.getFinalGradeObtained())
                .expectedFinishDate(createMenteeDTO.getExpectedFinishDate())
                .countriesOfStudy(countries)
                .internationalExams(exams)
                .areaOfStudies(studyAreas)
                .degreePursing(createMenteeDTO.getDegreePursing())
                .mentee(mentee)
                .parent(parent)
                .build();

        mentee.setMenteeDetails(menteeDetails);
        menteeDetailsRepository.save(menteeDetails);
        menteeRepository.save(mentee);
    }

    public Mentee getMenteeById(Long id) {
        return menteeRepository.findById(id).orElse(null);
    }

    public MenteeDTO mapMenteeToDTO(Mentee mentee) {
        return new MenteeDTO();
    }
}
