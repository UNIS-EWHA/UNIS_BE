package com.ewha.unis.member.repository;

import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.domain.MemberPart;
import com.ewha.unis.member.domain.MemberRole;
import com.ewha.unis.member.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);
    Optional<Member> findByLoginId(String loginId);
    long countByMemberRole(MemberRole memberRole);
    long countByRole(Role role);
    List<Member> findAllByRoleIn(List<Role> roles);

    @Query("""
            SELECT m FROM Member m
            WHERE (:part IS NULL OR m.part = :part)
            """)
    Page<Member> search(@Param("part") MemberPart part, Pageable pageable);
}
