package com.ewha.unis.about.dto;

import com.ewha.unis.about.entity.Photo;

public record PhotoResponse(
        Long photoId,
        String label,
        String imageUrl
) {
    public static PhotoResponse from(Photo photo) {
        return new PhotoResponse(photo.getId(), photo.getLabel(), photo.getImageUrl());
    }
}
