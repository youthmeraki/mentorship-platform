package com.youthmeraki.mentorshipplatform.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.youthmeraki.mentorshipplatform.dtos.CreateMenteeDTO;
import com.youthmeraki.mentorshipplatform.dtos.TempMentee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class TwoStepVerificationService {

    @Value("${spring.mail.from-address}")
    private String fromAddress;

    @Value("${spring.mail.verification-base-url}")
    private String verificationBaseUrl;

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;
    private final RedisTemplate<String, TempMentee> redisTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final static Duration TTL = Duration.ofHours(2);


    public TwoStepVerificationService(RedisTemplate<String, TempMentee> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(TempMentee user) {
        String token = user.getVerificationToken();
        redisTemplate.opsForValue().set(token, user);
    }

    public TempMentee get(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    public void delete(String token) {
        redisTemplate.delete(token);
    }

    public String generateTwoStepVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public void sendVerificationEmail(String email, String token) {
        System.out.println("Sending verification email to: " + email+
                "\n from address: " + fromAddress);

        Email from = new Email(fromAddress);
        String subject = "Verify your account";
        Email to = new Email(email);
        String link = verificationBaseUrl + "/api/v1/user/verify?token=" + token;
        Content content = new Content("text/plain", "Click here to verify: " + link);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");

        try {
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TempMentee createTempMentee(CreateMenteeDTO createMenteeDTO) {

        return TempMentee.builder()
                .username(createMenteeDTO.getUsername())
                .name(createMenteeDTO.getName())
                .middleName(createMenteeDTO.getMiddleName())
                .surname(createMenteeDTO.getSurname())
                .email(createMenteeDTO.getEmail())
                .phone(createMenteeDTO.getPhone())
                .birthdate(createMenteeDTO.getBirthdate())
                .password(bCryptPasswordEncoder.encode(createMenteeDTO.getPassword()))
                .gender(createMenteeDTO.isGender())
                .verificationToken(generateTwoStepVerificationToken())
                .verificationTokenExpiry(Instant.now().plus(TTL).toEpochMilli())

                .subscription(createMenteeDTO.getSubscription())
                .nationality(createMenteeDTO.getNationality())
                .city(createMenteeDTO.getCity())
                .residingCountry(createMenteeDTO.getResidingCountry())
                .residingCity(createMenteeDTO.getResidingCity())
                .highestDegreeLevel(createMenteeDTO.getHighestDegreeLevel())
                .institutionName(createMenteeDTO.getInstitutionName())
                .finalGradeObtained(createMenteeDTO.getFinalGradeObtained())
                .expectedFinishDate(createMenteeDTO.getExpectedFinishDate())
                .degreePursing(createMenteeDTO.getDegreePursing())
                .parentDto(createMenteeDTO.getParentDto())
                .studyAreas(createMenteeDTO.getStudyAreas())
                .countriesOfStudy(createMenteeDTO.getCountriesOfStudy())
                .internationalExams(createMenteeDTO.getInternationalExams())

                .build();
    }

}
