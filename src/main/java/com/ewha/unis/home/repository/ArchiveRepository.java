package com.ewha.unis.home.repository;

import com.ewha.unis.home.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {
    List<Archive> findAllByOrderBySortOrderAsc();
}
