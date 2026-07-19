package com.ewha.unis.application.repository;

import com.ewha.unis.application.entity.Application;
import com.ewha.unis.application.entity.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByStudentIdAndGeneration(String studentId, Integer generation);

    long countByGeneration(Integer generation);

    long countByGenerationAndStatus(Integer generation, ApplicationStatus status);

    @Query("""
            SELECT a FROM Application a
            WHERE (:status IS NULL OR a.status = :status)
            ORDER BY a.createdAt DESC
            """)
    Page<Application> search(@Param("status") ApplicationStatus status, Pageable pageable);
}
