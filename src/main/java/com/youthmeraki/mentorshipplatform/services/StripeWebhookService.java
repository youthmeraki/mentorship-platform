package com.youthmeraki.mentorshipplatform.services;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.youthmeraki.mentorshipplatform.exceptions.WebhookProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StripeWebhookService {

    private final AdminService adminService;
    private final PaymentProcessingService paymentProcessingService;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @Async
    public void handleWebhookEvent(String payload, String signature) {
        try {
            Event event = Webhook.constructEvent(payload, signature, endpointSecret);

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getDataObjectDeserializer()
                        .getObject()
                        .orElseThrow(() -> new WebhookProcessingException("No session data available"));

                // Call transactional method from different service
                paymentProcessingService.processCompletedCheckout(session);
            }

        } catch (SignatureVerificationException e) {
            log.error("Stripe signature verification failed", e);
            throw new WebhookProcessingException("Invalid signature", e);
        } catch (Exception e) {
            log.error("Error processing Stripe webhook", e);
            throw new WebhookProcessingException("Error processing webhook", e);
        }
    }


}