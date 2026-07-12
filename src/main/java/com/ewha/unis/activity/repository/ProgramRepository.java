package com.ewha.unis.activity.repository;

import com.ewha.unis.activity.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findAllByOrderBySortOrderAsc();
}
