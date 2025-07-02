package com.youthmeraki.mentorshipplatform.exceptions;

public class EmailAlreadyExistsException extends AppException {
    public EmailAlreadyExistsException(String emailAlreadyRegistered) {
        super(emailAlreadyRegistered, "EMAIL_ALREADY_EXISTS");
    }
}
