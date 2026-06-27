package com.ewha.unis.auth.service;

import com.ewha.unis.auth.dto.LoginIdCheckResponse;
import com.ewha.unis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;

    public LoginIdCheckResponse checkAvailableLoginId(String loginId) {
        boolean available = !memberRepository.existsByLoginId(loginId);
        return LoginIdCheckResponse.of(available);
    }
}
