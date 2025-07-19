package com.youthmeraki.mentorshipplatform.controllers;

import com.youthmeraki.mentorshipplatform.models.video.Video;
import com.youthmeraki.mentorshipplatform.services.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/admin/videos")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication") // Applies to all endpoints in this controller
public class AdminVideoController {

    private final VideoService videoService;

    @PostMapping("/prepare")
    public ResponseEntity<Video> prepareVideoUpload(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Long moduleId) {
        Video video = videoService.createVideoEntry(title, description, moduleId);
        return ResponseEntity.ok(video);
    }

    @PostMapping(value = "/{videoId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload video file")
    public ResponseEntity<Void> uploadVideoFile(
            @PathVariable Long videoId,
            @RequestPart(required = true) MultipartFile file) {
        videoService.processVideoUpload(videoId, file);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{videoId}/status")
    public ResponseEntity<Video> getUploadStatus(@PathVariable Long videoId) {
        return ResponseEntity.ok(videoService.getVideoById(videoId));
    }
}