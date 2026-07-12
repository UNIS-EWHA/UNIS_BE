package com.ewha.unis.community.repository;

import com.ewha.unis.community.entity.Recruitment;
import com.ewha.unis.member.domain.MemberPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    @Query("""
            SELECT DISTINCT r FROM Recruitment r
            LEFT JOIN r.parts rp
            WHERE (:part IS NULL OR rp.part = :part)
            AND (:keyword IS NULL OR r.title LIKE CONCAT('%', :keyword, '%')
                 OR r.content LIKE CONCAT('%', :keyword, '%'))
            ORDER BY r.createdAt DESC
            """)
    Page<Recruitment> search(@Param("part") MemberPart part,
                              @Param("keyword") String keyword,
                              Pageable pageable);
}
