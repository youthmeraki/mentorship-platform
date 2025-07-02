package com.youthmeraki.mentorshipplatform.exceptions;

public class TokenExpiredException extends AppException {
    public TokenExpiredException(String verificationTokenHasExpired) {
        super(verificationTokenHasExpired, "TOKEN_EXPIRED");
    }
}
