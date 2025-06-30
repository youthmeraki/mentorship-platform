package com.youthmeraki.mentorshipplatform.models;

public enum Subscription {
    ESSENTIAL, PLATINUM;

    public static Subscription fromString(String value) {
        try {
            return Subscription.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("Invalid subscription type: " + value);
        }
    }
}
