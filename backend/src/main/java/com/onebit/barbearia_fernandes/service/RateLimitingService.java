package com.onebit.barbearia_fernandes.service;

import io.github.bucket4j.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingService.class);
    private final Map<String, Bucket> registerCache = new ConcurrentHashMap<>();
    private final Map<String, Bucket> loginCache = new ConcurrentHashMap<>();

    public Bucket resolveRegisterBucket(String ipAddress) {
        logger.info("Resolving register bucket for IP: {}", ipAddress);
        return registerCache.computeIfAbsent(ipAddress, this::newRegisterBucket);
    }

    public Bucket resolveLoginBucket(String ipAddress) {
        logger.info("Resolving login bucket for IP: {}", ipAddress);
        return loginCache.computeIfAbsent(ipAddress, this::newLoginBucket);
    }

    private Bucket newRegisterBucket(String ipAddress) {
        logger.info("Creating new register bucket for IP: {}", ipAddress);
        return Bucket.builder()
                .addLimit(limit ->
                        limit
                                .capacity(3)
                                .refillGreedy(3, Duration.ofMinutes(3))
                )
                .build();
    }

    private Bucket newLoginBucket(String ipAddress) {
        logger.info("Creating new login bucket for IP: {}", ipAddress);
        return Bucket.builder()
                .addLimit(limit ->
                        limit
                                .capacity(5)
                                .refillGreedy(5, Duration.ofMinutes(5))
                )
                .build();
    }

}
