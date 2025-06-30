package com.youthmeraki.mentorshipplatform.exceptions;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(String userId) {
        super("User with ID '" + userId + "' not found", "USER_NOT_FOUND");
    }
}