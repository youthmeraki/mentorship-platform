package com.youthmeraki.mentorshipplatform.services;

import com.youthmeraki.mentorshipplatform.dtos.CreateMenteeDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ValidationService {

    public void validateMenteeRegistration(CreateMenteeDTO dto) {
        List<String> errors = new ArrayList<>();

        if (!isValidUsername(dto.getUsername())) {
            errors.add("Username must be between 3 and 20 characters");
        }
        if (!isValidEmail(dto.getEmail())) {
            errors.add("Invalid email format");
        }
        if (!isValidPassword(dto.getPassword())) {
            errors.add("Password must be at least 8 characters");
        }
        if (!isValidPhone(dto.getPhone())) {
            errors.add("Invalid phone number format");
        }
        if (!isValidAge(dto.getBirthdate())) {
            errors.add("User must be at least 13 years old");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(", ", errors));
        }
    }

    private boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && username.length() <= 20;
    }
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^\\+?[0-9]{10,15}$");
    }
    private boolean isValidAge(Date birthdate) {
        if (birthdate == null) return false;
        long ageInMillis = System.currentTimeMillis() - birthdate.getTime();
        long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
        return ageInYears >= 13;
    }
}
