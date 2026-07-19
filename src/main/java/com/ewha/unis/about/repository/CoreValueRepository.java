package com.ewha.unis.about.repository;

import com.ewha.unis.about.entity.CoreValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoreValueRepository extends JpaRepository<CoreValue, Long> {
    List<CoreValue> findAllByOrderBySortOrderAsc();
}
