package com.youthmeraki.mentorshipplatform.services;

import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentProcessingService {

    private final AdminService adminService;

    @Transactional
    public void processCompletedCheckout(Session session) {
        String customerEmail = session.getCustomerDetails().getEmail();
        adminService.setPaidTrueByEmail(customerEmail);
        log.info("Processed payment for customer: {}", customerEmail);
    }
}