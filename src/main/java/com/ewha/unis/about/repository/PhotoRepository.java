package com.ewha.unis.about.repository;

import com.ewha.unis.about.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByOrderBySortOrderAsc();
}
