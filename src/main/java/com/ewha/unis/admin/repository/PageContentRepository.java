package com.ewha.unis.admin.repository;

import com.ewha.unis.admin.entity.PageContent;
import com.ewha.unis.admin.entity.PageSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageContentRepository extends JpaRepository<PageContent, Long> {
    Optional<PageContent> findBySection(PageSection section);
}
