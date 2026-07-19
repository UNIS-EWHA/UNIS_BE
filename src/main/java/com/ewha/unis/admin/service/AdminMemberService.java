package com.ewha.unis.admin.service;

import com.ewha.unis.admin.dto.MemberCreateResponse;
import com.ewha.unis.admin.dto.MemberListResponse;
import com.ewha.unis.admin.dto.MemberSaveRequest;
import com.ewha.unis.admin.dto.MemberStatsResponse;
import com.ewha.unis.admin.dto.MemberSummaryResponse;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.domain.MemberPart;
import com.ewha.unis.member.domain.MemberRole;
import com.ewha.unis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMemberService {
    private final MemberRepository memberRepository;

    public MemberStatsResponse getStats() {
        long total = memberRepository.count();
        long executive = memberRepository.countByMemberRole(MemberRole.EXECUTIVE);
        long partLeader = memberRepository.countByMemberRole(MemberRole.PART_LEADER);
        long general = memberRepository.countByMemberRole(MemberRole.GENERAL);
        return new MemberStatsResponse(total, executive, partLeader, general);
    }

    public MemberListResponse getMembers(MemberPart part, Pageable pageable) {
        Page<Member> page = memberRepository.search(part, pageable);
        var members = page.getContent().stream().map(MemberSummaryResponse::from).toList();
        return new MemberListResponse(members, page.getTotalElements(), page.hasNext());
    }

    @Transactional
    public MemberCreateResponse createMember(MemberSaveRequest request) {
        Member member = Member.builder()
                .name(request.name())
                .department(request.department())
                .part(request.part())
                .memberRole(request.role())
                .generation(request.generation())
                .build();
        memberRepository.save(member);
        return new MemberCreateResponse(member.getId());
    }

    @Transactional
    public void updateMember(Long memberId, MemberSaveRequest request) {
        Member member = getMemberOrThrow(memberId);
        member.updateRosterInfo(request.name(), request.department(), request.part(),
                request.role(), request.generation());
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = getMemberOrThrow(memberId);
        memberRepository.delete(member);
    }

    private Member getMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
    }
}
