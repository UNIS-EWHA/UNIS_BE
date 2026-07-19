package com.ewha.unis.project.repository;

import com.ewha.unis.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
            SELECT DISTINCT p FROM Project p
            LEFT JOIN p.techStacks t
            WHERE (:generation IS NULL OR p.generation = :generation)
            AND (:keyword IS NULL OR p.name LIKE CONCAT('%', :keyword, '%')
                 OR t.techName LIKE CONCAT('%', :keyword, '%'))
            ORDER BY p.createdAt DESC
            """)
    Page<Project> search(@Param("generation") Integer generation,
                          @Param("keyword") String keyword,
                          Pageable pageable);

    @Query("SELECT MAX(p.generation) FROM Project p")
    Integer findMaxGeneration();
}
