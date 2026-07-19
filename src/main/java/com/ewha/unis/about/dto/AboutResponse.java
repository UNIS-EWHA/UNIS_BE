package com.ewha.unis.about.dto;

import java.util.List;

public record AboutResponse(
        List<CoreValueResponse> coreValues,
        List<PartResponse> parts,
        List<PhotoResponse> photos,
        List<FaqResponse> faqs
) {
}
