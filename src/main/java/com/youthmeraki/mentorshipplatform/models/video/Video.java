package com.youthmeraki.mentorshipplatform.models.video;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Video {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String url; // Nullable during upload
    private String thumbnailUrl; // Nullable during upload

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UploadStatus status = UploadStatus.PENDING;

    private String muxUploadId; // Store Mux upload ID for tracking

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    public enum UploadStatus {
        PENDING,
        UPLOADING,
        PROCESSING,
        COMPLETED,
        FAILED
    }

}