package com.youthmeraki.mentorshipplatform.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto {
    // Getters and Setters
    @Getter
    private Long id;  // Using UUID to avoid exposing sequential IDs
    private Long senderId;
    private String senderName;
    private Long recipientId;
    private String recipientName;
    private String content;
    private LocalDateTime timestamp;
    private MessageStatus status;
    private boolean isMine;  // Frontend convenience field

    // Constructors
    public ChatMessageDto() {
    }

    public ChatMessageDto(Long senderId, Long recipientId, String content) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.status = MessageStatus.SENDING;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    // Utility methods
    public String getFormattedTimestamp() {
        return timestamp.toString(); // Or use DateTimeFormatter for custom format
    }

    @Override
    public String toString() {
        return "ChatMessageDto{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", recipientId=" + recipientId +
                ", recipientName='" + recipientName + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", isMine=" + isMine +
                '}';
    }

    // Enum for message status
    public enum MessageStatus {
        SENDING,  // Client-side only - message is being sent
        DELIVERED,  // Message reached server
        READ,  // Recipient has seen the message
        FAILED  // Sending failed
    }
}