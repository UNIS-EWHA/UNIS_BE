package com.ewha.unis.activity.service;

import com.ewha.unis.activity.dto.ActivityResponse;
import com.ewha.unis.activity.dto.CurriculumResponse;
import com.ewha.unis.activity.dto.ProgramResponse;
import com.ewha.unis.activity.repository.CurriculumStepRepository;
import com.ewha.unis.activity.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityService {
    private final ProgramRepository programRepository;
    private final CurriculumStepRepository curriculumStepRepository;

    public ActivityResponse getActivity() {
        return new ActivityResponse(
                programRepository.findAllByOrderBySortOrderAsc().stream()
                        .map(ProgramResponse::from).toList(),
                curriculumStepRepository.findAllByOrderByStepOrderAsc().stream()
                        .map(CurriculumResponse::from).toList()
        );
    }
}
