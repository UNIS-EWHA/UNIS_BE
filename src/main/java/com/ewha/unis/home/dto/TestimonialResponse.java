package com.ewha.unis.home.dto;

import com.ewha.unis.home.entity.Testimonial;

public record TestimonialResponse(
        Long testimonialId,
        String name,
        String role,
        String content
) {
    public static TestimonialResponse from(Testimonial testimonial) {
        return new TestimonialResponse(
                testimonial.getId(),
                testimonial.getName(),
                testimonial.getRole(),
                testimonial.getContent()
        );
    }
}
