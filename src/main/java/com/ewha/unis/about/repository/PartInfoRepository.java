package com.ewha.unis.about.repository;

import com.ewha.unis.about.entity.PartInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartInfoRepository extends JpaRepository<PartInfo, Long> {
    List<PartInfo> findAllByOrderBySortOrderAsc();
}
