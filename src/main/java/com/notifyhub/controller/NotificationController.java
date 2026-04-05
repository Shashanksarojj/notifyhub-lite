package com.notifyhub.controller;

import com.notifyhub.dto.NotificationRequest;
import com.notifyhub.dto.NotificationResponse;
import com.notifyhub.entity.Notification;
import com.notifyhub.exception.RateLimitExceededException;
import com.notifyhub.ratelimiter.InMemoryRateLimiter;
import com.notifyhub.repository.NotificationRepository;
import com.notifyhub.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;
    private final InMemoryRateLimiter rateLimiter;

    @PostMapping
    public NotificationResponse createNotification(
            @Valid @RequestBody NotificationRequest request,
            HttpServletRequest httpRequest
    ) {

        String key = httpRequest.getRemoteAddr();
        log.info("Incoming request to create notification for {}",
                request.getRecipient());

        if (!rateLimiter.allowRequest(key)) {
            throw new RateLimitExceededException("Too many requests. Please try later.");
        }

        return notificationService.createNotification(request);
    }

    @GetMapping
    public List<NotificationResponse> getAllNotifications() {
        return notificationService.getAllNotifications();
    }
}