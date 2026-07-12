package com.ewha.unis.admin.repository;

import com.ewha.unis.community.entity.CommunityPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 게시글(창업 정보) + 구인글을 한 목록으로 합쳐 최신순 페이지네이션하기 위한 조회 전용 리포지토리.
 * 두 테이블에 걸친 UNION 페이지네이션은 JPQL로 표현할 수 없어 native query를 사용한다.
 */
public interface AdminCommunityQueryRepository extends Repository<CommunityPost, Long> {

    @Query(value = """
            SELECT * FROM (
                SELECT p.post_id AS targetId, 'POST' AS type, m.name AS author,
                       p.title AS title, p.created_at AS createdAt, p.view_count AS viewCount
                FROM community_posts p JOIN members m ON p.user_id = m.member_id
                UNION ALL
                SELECT r.recruitment_id AS targetId, 'RECRUITMENT' AS type, m.name AS author,
                       r.title AS title, r.created_at AS createdAt, r.view_count AS viewCount
                FROM recruitments r JOIN members m ON r.user_id = m.member_id
            ) combined
            ORDER BY createdAt DESC
            LIMIT :size OFFSET :offset
            """, nativeQuery = true)
    List<AdminPostSummaryProjection> findAllCombined(@Param("size") int size, @Param("offset") long offset);

    @Query(value = """
            SELECT (SELECT COUNT(*) FROM community_posts) + (SELECT COUNT(*) FROM recruitments)
            """, nativeQuery = true)
    long countCombined();
}
