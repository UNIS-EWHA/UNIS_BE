package com.ewha.unis.about.repository;

import com.ewha.unis.about.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findAllByOrderBySortOrderAsc();

    @Query("SELECT COALESCE(MAX(f.sortOrder), -1) FROM Faq f")
    int findMaxSortOrder();
}
