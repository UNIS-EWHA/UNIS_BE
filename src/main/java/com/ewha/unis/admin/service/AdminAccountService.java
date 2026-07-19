package com.ewha.unis.admin.service;

import com.ewha.unis.admin.dto.AdminAccountGrantRequest;
import com.ewha.unis.admin.dto.AdminAccountResponse;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.domain.Role;
import com.ewha.unis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminAccountService {
    private final MemberRepository memberRepository;

    public List<AdminAccountResponse> getAccounts() {
        return memberRepository.findAllByRoleIn(List.of(Role.ADMIN, Role.SUPER_ADMIN)).stream()
                .map(AdminAccountResponse::from)
                .toList();
    }

    @Transactional
    public void grantAdmin(AdminAccountGrantRequest request) {
        Member member = memberRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        if (request.role() != Role.SUPER_ADMIN) {
            assertNotLastSuperAdmin(member);
        }
        member.promoteTo(request.role());
    }

    @Transactional
    public void revokeAdmin(Long accountId) {
        Member member = memberRepository.findById(accountId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        assertNotLastSuperAdmin(member);
        member.promoteTo(Role.USER);
    }

    private void assertNotLastSuperAdmin(Member member) {
        if (member.getRole() == Role.SUPER_ADMIN && memberRepository.countByRole(Role.SUPER_ADMIN) <= 1) {
            throw new CustomException(ErrorCode.LAST_SUPER_ADMIN_CANNOT_BE_DEMOTED);
        }
    }
}
