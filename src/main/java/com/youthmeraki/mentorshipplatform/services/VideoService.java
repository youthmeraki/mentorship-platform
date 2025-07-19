package com.youthmeraki.mentorshipplatform.services;

import com.mux.ApiException;
import com.mux.sdk.AssetsApi;
import com.mux.sdk.DirectUploadsApi;
import com.mux.sdk.models.*;
import com.youthmeraki.mentorshipplatform.dtos.MuxWebhookEvent;
import com.youthmeraki.mentorshipplatform.models.video.Module;
import com.youthmeraki.mentorshipplatform.models.video.Video;
import com.youthmeraki.mentorshipplatform.repositories.ModuleRepository;
import com.youthmeraki.mentorshipplatform.repositories.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    private final static int MAX_RETRIES = 3;
    private final DirectUploadsApi directUploadApi;
    private final AssetsApi assetsApi;
    private final VideoRepository videoRepository;
    private final ModuleRepository moduleRepository;

    public Video getVideoById(Long videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found"));
    }

    @Transactional
    public Video createVideoEntry(String title, String description, Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new EntityNotFoundException("Module not found"));

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setStatus(Video.UploadStatus.PENDING);
        video.setModule(module);
        return videoRepository.save(video);
    }

    @Transactional
    public void processVideoUpload(Long videoId, MultipartFile file) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found"));

        if (video.getStatus() == Video.UploadStatus.COMPLETED) {
            throw new IllegalStateException("Video already processed");
        }

        video.setStatus(Video.UploadStatus.UPLOADING);
        videoRepository.save(video);

        try {
            CreateUploadRequest uploadRequest = new CreateUploadRequest();
            uploadRequest.setNewAssetSettings(new CreateAssetRequest()
                    .playbackPolicy(List.of(PlaybackPolicy.PUBLIC))
                    .passthrough(videoId.toString())); // Pass video ID to Mux

            UploadResponse uploadResponse = directUploadApi.createDirectUpload(uploadRequest).execute();
            video.setMuxUploadId(Objects.requireNonNull(uploadResponse.getData()).getId());
            videoRepository.save(video);

            uploadWithRetry(Objects.requireNonNull(uploadResponse.getData()).getUrl(), file.getBytes(), video);
        } catch (ApiException e) {
            log.error("Mux API error - Status: {}, Body: {}, Headers: {}",
                    e.getCode(),
                    e.getResponseBody(),
                    e.getResponseHeaders());
            throw new RuntimeException("Mux API error: " + e.getMessage(), e);
        } catch (Exception e) {
            video.setStatus(Video.UploadStatus.FAILED);
            videoRepository.save(video);
            log.error("Failed to initialize upload for video {}: {}", videoId, e.getMessage(), e); // Add proper logging
            throw new RuntimeException("Upload initialization failed", e);
        }
    }


    public void handleMuxWebhook(MuxWebhookEvent event) {
        if ("video.upload.asset_created".equals(event.getType())) {
            // Update status when Mux acknowledges the upload
            Video video = videoRepository.findByMuxUploadId(event.getData().getUploadId())
                    .orElseThrow(() -> new EntityNotFoundException("Video not found"));
            video.setStatus(Video.UploadStatus.PROCESSING);
            videoRepository.save(video);
        } else if ("video.asset.ready".equals(event.getType())) {
            AssetResponse asset = null;
            try {
                asset = assetsApi.getAsset(event.getData().getAssetId()).execute();
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
            Long videoId = Long.parseLong(Objects.requireNonNull(
                    Objects.requireNonNull(
                            asset.getData()).getPassthrough()
            ));
            String playbackId = Objects.requireNonNull(
                    asset.getData().getPlaybackIds()).getFirst().getId();
            String url = String.format("https://stream.mux.com/%s.m3u8", playbackId);
            String thumbnailUrl = String.format("https://image.mux.com/%s/thumbnail.jpg", playbackId);
            Video video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new EntityNotFoundException("Video not found"));

            video.setUrl(url);
            video.setThumbnailUrl(thumbnailUrl);
            video.setStatus(Video.UploadStatus.COMPLETED);
            videoRepository.save(video);
        } else if (event.getType().contains("error")) {
            // Handle error cases
            Video video = videoRepository.findByMuxUploadId((event.getData().getUploadId()))
                    .orElseThrow(() -> new EntityNotFoundException("Video not found"));
            video.setStatus(Video.UploadStatus.FAILED);
            videoRepository.save(video);
        }
    }

    private void uploadWithRetry(String uploadUrl, byte[] fileData, Video video) {
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try {
                uploadToMux(uploadUrl, fileData);
                video.setStatus(Video.UploadStatus.PROCESSING);
                videoRepository.save(video);
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt == MAX_RETRIES) {
                    video.setStatus(Video.UploadStatus.FAILED);
                    videoRepository.save(video);
                    throw new RuntimeException("Upload failed after " + MAX_RETRIES + " attempts", e);
                }
                // Wait before retrying
                try {
                    Thread.sleep(1000L * attempt);
                } catch (InterruptedException ie) {
                }
            }
        }
    }

    private void uploadToMux(String uploadUrl, byte[] fileData) {
        if (uploadUrl == null || uploadUrl.isEmpty()) {
            throw new IllegalArgumentException("Upload URL cannot be null or empty");
        }
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uploadUrl))
                    .header("Content-Type", "video/mp4")
                    .PUT(HttpRequest.BodyPublishers.ofByteArray(fileData))
                    .build();

            try {
                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() >= 300) {
                    throw new IOException("Upload failed with status: " + response.statusCode());
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload to Mux", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during Mux upload", e);
        }
    }
}