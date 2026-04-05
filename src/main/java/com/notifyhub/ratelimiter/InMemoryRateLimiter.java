package com.notifyhub.ratelimiter;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRateLimiter {

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_SIZE_MS = 60 * 1000;

    private final Map<String, UserRequestInfo> requestCounts = new ConcurrentHashMap<>();

    public boolean allowRequest(String key) {

        long currentTime = Instant.now().toEpochMilli();

        requestCounts.putIfAbsent(key, new UserRequestInfo(0, currentTime));

        UserRequestInfo info = requestCounts.get(key);

        synchronized (info) {

            if (currentTime - info.windowStart > WINDOW_SIZE_MS) {
                info.count = 0;
                info.windowStart = currentTime;
            }

            if (info.count < MAX_REQUESTS) {
                info.count++;
                return true;
            }

            return false;
        }
    }

    private static class UserRequestInfo {
        int count;
        long windowStart;

        UserRequestInfo(int count, long windowStart) {
            this.count = count;
            this.windowStart = windowStart;
        }
    }
}