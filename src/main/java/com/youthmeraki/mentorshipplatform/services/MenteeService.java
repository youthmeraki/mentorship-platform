package com.youthmeraki.mentorshipplatform.services;

import com.youthmeraki.mentorshipplatform.dtos.CreateMenteeDTO;
import com.youthmeraki.mentorshipplatform.dtos.MenteeDTO;
import com.youthmeraki.mentorshipplatform.exceptions.UserIsAMenteeException;
import com.youthmeraki.mentorshipplatform.exceptions.UserNotFoundException;
import com.youthmeraki.mentorshipplatform.models.Mentee;
import com.youthmeraki.mentorshipplatform.models.Subscription;
import com.youthmeraki.mentorshipplatform.models.User;
import com.youthmeraki.mentorshipplatform.repositories.MenteeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MenteeService {

    private final MenteeRepo menteeRepository;
    private final UserService userService;
    private final JwtService jwtService;


    public MenteeService(MenteeRepo menteeRepository, UserService userService, JwtService jwtService) {
        this.menteeRepository = menteeRepository;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public List<Mentee> getAllMentees() {
        return menteeRepository.findAll();
    }

    @Transactional
    public MenteeDTO createMentee(CreateMenteeDTO createMenteeDTO, String token) {

        String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UserNotFoundException(username);
        }

        if (menteeRepository.existsByUser(user)) {
            throw new UserIsAMenteeException(username);
        }

        Mentee mentee = new Mentee();
        mentee.setUser(user);
        Subscription subscription = Subscription.fromString(createMenteeDTO.getSubscription());
        mentee.setSubscription(subscription);
        mentee = menteeRepository.save(mentee);
        return mapMenteeToDTO(mentee);
    }

    public Mentee getMenteeById(Long id) {
        return menteeRepository.findById(id).orElse(null);
    }

    public MenteeDTO mapMenteeToDTO(Mentee mentee) {
        return new MenteeDTO();
    }
}
