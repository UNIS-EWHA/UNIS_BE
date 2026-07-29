package com.ewha.unis.community.repository;

import com.ewha.unis.community.entity.CommunityPost;
import com.ewha.unis.community.entity.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

    @Query("""
            SELECT p FROM CommunityPost p
            WHERE (CAST(:category AS text) IS NULL OR p.category = :category)
            AND (CAST(:keyword AS text) IS NULL OR p.title LIKE CONCAT('%', CAST(:keyword AS text), '%')
                 OR p.content LIKE CONCAT('%', CAST(:keyword AS text), '%'))
            ORDER BY p.createdAt DESC
            """)
    Page<CommunityPost> search(@Param("category") PostCategory category,
                                @Param("keyword") String keyword,
                                Pageable pageable);
}
