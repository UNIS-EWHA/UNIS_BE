package com.ewha.unis.activity.repository;

import com.ewha.unis.activity.entity.CurriculumStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurriculumStepRepository extends JpaRepository<CurriculumStep, Long> {
    List<CurriculumStep> findAllByOrderByStepOrderAsc();
}
