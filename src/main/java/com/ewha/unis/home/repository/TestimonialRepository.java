package com.ewha.unis.home.repository;

import com.ewha.unis.home.entity.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
    List<Testimonial> findAllByOrderBySortOrderAsc();
}
