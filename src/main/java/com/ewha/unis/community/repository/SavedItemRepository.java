package com.ewha.unis.community.repository;

import com.ewha.unis.community.entity.SavedItem;
import com.ewha.unis.community.entity.SavedTargetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SavedItemRepository extends JpaRepository<SavedItem, Long> {
    Optional<SavedItem> findByUser_IdAndTargetTypeAndTargetId(Long userId, SavedTargetType targetType, Long targetId);
    List<SavedItem> findAllByUser_IdAndTargetType(Long userId, SavedTargetType targetType);
    void deleteAllByTargetTypeAndTargetId(SavedTargetType targetType, Long targetId);

    @Query("""
            SELECT s FROM SavedItem s
            WHERE s.user.id = :userId
            AND (:targetType IS NULL OR s.targetType = :targetType)
            ORDER BY s.createdAt DESC
            """)
    Page<SavedItem> search(@Param("userId") Long userId,
                            @Param("targetType") SavedTargetType targetType,
                            Pageable pageable);
}
