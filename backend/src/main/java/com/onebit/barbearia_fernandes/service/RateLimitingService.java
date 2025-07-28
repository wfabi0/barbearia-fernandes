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

    public final int REGISTER_CAPACITY = 3;
    public final int LOGIN_CAPACITY = 5;
    public final Duration REGISTER_REFILL_DURATION = Duration.ofMinutes(3);
    public final Duration LOGIN_REFILL_DURATION = Duration.ofMinutes(5);

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
                                .capacity(REGISTER_CAPACITY)
                                .refillGreedy(REGISTER_CAPACITY, REGISTER_REFILL_DURATION)
                )
                .build();
    }

    private Bucket newLoginBucket(String ipAddress) {
        logger.info("Creating new login bucket for IP: {}", ipAddress);
        return Bucket.builder()
                .addLimit(limit ->
                        limit
                                .capacity(LOGIN_CAPACITY)
                                .refillGreedy(LOGIN_CAPACITY, LOGIN_REFILL_DURATION)
                )
                .build();
    }

}
