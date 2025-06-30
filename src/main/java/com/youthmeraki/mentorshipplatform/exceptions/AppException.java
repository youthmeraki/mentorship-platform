package com.youthmeraki.mentorshipplatform.exceptions;

import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {
    private final String errorCode;

    public AppException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

