package com.ewha.unis.auth.service;

import com.ewha.unis.auth.dto.*;
import com.ewha.unis.auth.security.CustomUserDetails;
import com.ewha.unis.global.config.jwt.JwtProperties;
import com.ewha.unis.global.config.jwt.JwtProvider;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.domain.Role;
import com.ewha.unis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthRedisService authRedisService;
    private final JwtProperties jwtProperties;

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

    @Transactional
    public LoginResult login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.loginId(), request.password()));

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Long memberId = principal.getMemberId();
        Role role = principal.getRole();

        long refreshValidity = request.keepLogin()
                ? jwtProperties.refreshTokenValidity()
                : jwtProperties.refreshTokenValidityShort();

        String accessToken = jwtProvider.createAccessToken(memberId, role);
        String refreshToken = jwtProvider.createRefreshToken(memberId, refreshValidity);
        authRedisService.saveRefreshToken(memberId, refreshToken, refreshValidity);

        memberRepository.findById(memberId).ifPresent(Member::updateLastLogin);

        return new LoginResult(accessToken, refreshToken, refreshValidity, role);
    }

    public LoginResult reissue(String refreshToken) {
        if (refreshToken == null || !jwtProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_INVALID);
        }
        Long memberId = jwtProvider.getMemberId(refreshToken);
        if (!authRedisService.isValidRefreshToken(memberId, refreshToken)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_INVALID);
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        long validity = jwtProperties.refreshTokenValidity();
        String newAccess = jwtProvider.createAccessToken(member.getId(), member.getRole());
        String newRefresh = jwtProvider.createRefreshToken(member.getId(), validity);
        authRedisService.saveRefreshToken(member.getId(), newRefresh, validity);
        return new LoginResult(newAccess, newRefresh, validity, member.getRole());
    }

    public void logout(String bearer, String refreshToken) {
        if (bearer != null && bearer.startsWith("Bearer ")) {
            String access = bearer.substring(7);
            if (jwtProvider.validateToken(access)) {
                authRedisService.blacklistAccessToken(access, jwtProvider.getRemainingSeconds(access));
            }
        }
        if (refreshToken != null && jwtProvider.validateToken(refreshToken)) {
            authRedisService.deleteRefreshToken(jwtProvider.getMemberId(refreshToken));
        }
    }
}
