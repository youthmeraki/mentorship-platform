package com.youthmeraki.mentorshipplatform.controllers;

import com.youthmeraki.mentorshipplatform.dtos.ChatMessageDto;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    public ChatMessageDto sendMessage(
            @Payload ChatMessageDto message
    ) {
        // Logic to send a chat message
        // This is a placeholder; actual implementation will depend on the chat service used
        return message;
    }
}
