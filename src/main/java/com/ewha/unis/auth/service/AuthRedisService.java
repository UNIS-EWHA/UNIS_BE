package com.ewha.unis.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthRedisService {
    private final StringRedisTemplate stringRedisTemplate;
    private static final String REFRESH_PREFIX = "refresh:token:";

    public void saveRefreshToken(Long memberId, String token, long validityMs) {
        stringRedisTemplate.opsForValue()
                .set(REFRESH_PREFIX + memberId, token, validityMs, TimeUnit.MILLISECONDS);
    }

    public boolean isValidRefreshToken(Long memberId, String token) {
        String stored = stringRedisTemplate.opsForValue().get(REFRESH_PREFIX + memberId);
        return stored != null && stored.equals(token);
    }

    public void deleteRefreshToken(Long memberId) {
        stringRedisTemplate.delete(REFRESH_PREFIX + memberId);
    }
}
