package com.ewha.unis.admin.repository;

import java.time.LocalDateTime;

public interface AdminPostSummaryProjection {
    Long getTargetId();
    String getType();
    String getAuthor();
    String getTitle();
    LocalDateTime getCreatedAt();
    Integer getViewCount();
}
