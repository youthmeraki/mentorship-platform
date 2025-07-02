package com.youthmeraki.mentorshipplatform.exceptions;

public class UsernameAlreadyExistsException extends AppException{

    public UsernameAlreadyExistsException(String username) {
        super("User with username '" + username + "' already exists", "DUPLICATED_KEY");
    }
}
