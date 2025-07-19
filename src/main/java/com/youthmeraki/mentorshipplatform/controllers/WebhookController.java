package com.youthmeraki.mentorshipplatform.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youthmeraki.mentorshipplatform.dtos.MuxWebhookEvent;
import com.youthmeraki.mentorshipplatform.exceptions.WebhookProcessingException;
import com.youthmeraki.mentorshipplatform.services.StripeWebhookService;
import com.youthmeraki.mentorshipplatform.services.VideoService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final VideoService videoService;
    private final StripeWebhookService stripeWebhookService;

    @Value("${mux.webhook.secret}")
    private String webhookSecret;


    @PostMapping("/mux")
    public ResponseEntity<Void> handleMuxWebhook(
            @RequestBody String rawPayload,
            @RequestHeader("Mux-Signature") String signatureHeader) {

        try {
            // 1. Verify the signature
            verifyMuxSignature(rawPayload, signatureHeader);

            // 2. Parse the payload
            MuxWebhookEvent event = parseWebhookPayload(rawPayload);

            // 3. Process the event
            videoService.handleMuxWebhook(event);

            return ResponseEntity.ok().build();

        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,
                                                    @RequestHeader("Stripe-Signature") String signature) {
        try {
            stripeWebhookService.handleWebhookEvent(payload, signature);
            return ResponseEntity.accepted().body("Webhook received and processing");
        } catch (WebhookProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    private void verifyMuxSignature(String payload, String signatureHeader)
            throws SecurityException {

        if (StringUtils.isEmpty(webhookSecret)) {
            throw new SecurityException("Webhook signing secret not configured");
        }

        if (StringUtils.isEmpty(signatureHeader)) {
            throw new SecurityException("Missing Mux-Signature header");
        }

        // Calculate expected signature
        String expectedSignature = "v1," +
                HmacUtils.hmacSha256Hex(webhookSecret, payload);

        // Compare signatures in constant time
        if (!MessageDigest.isEqual(
                expectedSignature.getBytes(StandardCharsets.UTF_8),
                signatureHeader.getBytes(StandardCharsets.UTF_8))) {
            throw new SecurityException("Invalid webhook signature");
        }
    }

    private MuxWebhookEvent parseWebhookPayload(String payload)
            throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(payload, MuxWebhookEvent.class);
    }
}
