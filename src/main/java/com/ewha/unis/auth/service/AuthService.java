package com.ewha.unis.auth.service;

import com.ewha.unis.auth.dto.LoginIdCheckResponse;
import com.ewha.unis.auth.dto.SignUpRequest;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;

    @Transactional
    public void signUp(SignUpRequest request) {
        emailVerificationService.verifyAndConsumeToken(request.email(), request.emailVerificationToken());

        if (memberRepository.existsByEmail(request.email())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        if (memberRepository.existsByLoginId(request.loginId())) {
            throw new CustomException(ErrorCode.DUPLICATE_LOGIN_ID);
        }

        String encodePassword = passwordEncoder.encode(request.password());

        Member member = Member.builder()
                .name(request.name())
                .email(request.email())
                .loginId(request.loginId())
                .encodedPassword(encodePassword)
                .generation(request.generation())
                .part(request.memberPart())
                .build();

        memberRepository.save(member);
    }

    public LoginIdCheckResponse checkAvailableLoginId(String loginId) {
        boolean available = !memberRepository.existsByLoginId(loginId);
        return LoginIdCheckResponse.of(available);
    }
}
