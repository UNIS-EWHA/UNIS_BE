package com.ewha.unis.auth.service;

import com.ewha.unis.auth.dto.EmailVerifyResponse;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.infra.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private final MailService mailService;
    private final StringRedisTemplate stringRedisTemplate;

    private static final String CODE_PREFIX = "email:code:";
    private static final String TOKEN_PREFIX = "email:token:";
    private static final long CODE_TTL = 5;
    private static final long TOKEN_TTL = 30;

    public void sendCode(String email) {
        String code = generateCode();
        stringRedisTemplate.opsForValue()
                .set(CODE_PREFIX + email, code, CODE_TTL, TimeUnit.MINUTES);
        mailService.sendVerificationCode(email, code);
    }

    public EmailVerifyResponse verifyCode(String email, String inputCode) {
        String storedCode = stringRedisTemplate.opsForValue().get(CODE_PREFIX + email);

        if (storedCode == null) {
            throw new CustomException(ErrorCode.EMAIL_CODE_EXPIRED);
        }
        if (!storedCode.equals(inputCode)) {
            throw new CustomException(ErrorCode.EMAIL_CODE_INVALID);
        }

        stringRedisTemplate.delete(CODE_PREFIX + email);

        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        stringRedisTemplate.opsForValue()
                .set(TOKEN_PREFIX + token, email, TOKEN_TTL, TimeUnit.MINUTES);

        return EmailVerifyResponse.of(token);
    }

    public void verifyAndConsumeToken(String email, String token) {
        String key = TOKEN_PREFIX + token;

        String storedEmail = stringRedisTemplate.opsForValue().getAndDelete(key);

        if (storedEmail == null) {
            throw new CustomException(ErrorCode.EMAIL_TOKEN_INVALID);
        }
        if (!storedEmail.equals(email)) {
            throw new CustomException(ErrorCode.EMAIL_TOKEN_INVALID);
        }
    }

    private String generateCode() {
        return String.format("%06d", new SecureRandom().nextInt(1_000_000));
    }
}
