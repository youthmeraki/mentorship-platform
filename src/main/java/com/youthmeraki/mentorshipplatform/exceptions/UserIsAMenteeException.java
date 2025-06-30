package com.youthmeraki.mentorshipplatform.exceptions;

public class UserIsAMenteeException extends AppException{

    public UserIsAMenteeException(String userId) {
        super("User with ID '" + userId + "' is already a mentee", "DUPLICATED_KEY");
    }
}
