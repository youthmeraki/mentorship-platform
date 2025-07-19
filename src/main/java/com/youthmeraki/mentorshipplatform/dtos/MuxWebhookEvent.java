package com.youthmeraki.mentorshipplatform.dtos;

import lombok.Data;

import java.util.List;

@Data
public class MuxWebhookEvent {
    private String type;
    private WebhookData data;
    private String object;
    private String id;
    private String environment;
    private String requestId;
    private String createdAt;

    // Helper methods to check event types
    public boolean isUploadEvent() {
        return type != null && type.startsWith("video.upload");
    }

    public boolean isAssetEvent() {
        return type != null && type.startsWith("video.asset");
    }

    public boolean isErrorEvent() {
        return type != null && (
                type.endsWith(".error") ||
                        (data != null && data.getErrorType() != null)
        );
    }

    @Data
    public static class WebhookData {
        // Common fields
        private String id;                  // Upload ID or Asset ID
        private String uploadId;            // For upload-related events
        private String assetId;             // For asset-related events
        private String passthrough;        // Your custom data (video ID)
        private String status;              // Current status
        private String errorType;           // For error events
        private String errorMessage;        // For error events
        private String payload;

        // Asset-specific fields
        private List<PlaybackId> playbackIds;
        private String thumbnailUrl;
        private Double duration;

        // Upload-specific fields
        private String assetStatus;
        private String corsOrigin;
        private String url;
        private String test;
    }

    @Data
    public static class PlaybackId {
        private String id;
        private String policy;
    }
}