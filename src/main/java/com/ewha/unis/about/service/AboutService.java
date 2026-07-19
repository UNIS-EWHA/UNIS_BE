package com.ewha.unis.about.service;

import com.ewha.unis.about.dto.AboutResponse;
import com.ewha.unis.about.dto.CoreValueResponse;
import com.ewha.unis.about.dto.FaqResponse;
import com.ewha.unis.about.dto.PartResponse;
import com.ewha.unis.about.dto.PhotoResponse;
import com.ewha.unis.about.repository.CoreValueRepository;
import com.ewha.unis.about.repository.FaqRepository;
import com.ewha.unis.about.repository.PartInfoRepository;
import com.ewha.unis.about.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AboutService {
    private final CoreValueRepository coreValueRepository;
    private final PartInfoRepository partInfoRepository;
    private final PhotoRepository photoRepository;
    private final FaqRepository faqRepository;

    public AboutResponse getAbout() {
        return new AboutResponse(
                coreValueRepository.findAllByOrderBySortOrderAsc().stream()
                        .map(CoreValueResponse::from).toList(),
                partInfoRepository.findAllByOrderBySortOrderAsc().stream()
                        .map(PartResponse::from).toList(),
                photoRepository.findAllByOrderBySortOrderAsc().stream()
                        .map(PhotoResponse::from).toList(),
                faqRepository.findAllByOrderBySortOrderAsc().stream()
                        .map(FaqResponse::from).toList()
        );
    }
}
