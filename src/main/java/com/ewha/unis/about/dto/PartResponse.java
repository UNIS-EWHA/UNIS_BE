package com.ewha.unis.about.dto;

import com.ewha.unis.about.entity.PartInfo;
import com.ewha.unis.about.entity.PartTag;

import java.util.List;

public record PartResponse(
        String name,
        String description,
        List<String> tags
) {
    public static PartResponse from(PartInfo partInfo) {
        return new PartResponse(
                partInfo.getName(),
                partInfo.getDescription(),
                partInfo.getTags().stream().map(PartTag::getTagName).toList()
        );
    }
}
