package com.ewha.unis.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recruit_settings")
public class RecruitSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_settings_id")
    private Long id;

    @Column(nullable = false)
    private Integer generation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "result_announce_at")
    private LocalDateTime resultAnnounceAt;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false, length = 100)
    private String schedule;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private RecruitSettings(Integer generation, RecruitStatus status, LocalDate startDate, LocalDate endDate,
                             LocalDateTime resultAnnounceAt, Integer capacity, String schedule) {
        this.generation = generation;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.resultAnnounceAt = resultAnnounceAt;
        this.capacity = capacity;
        this.schedule = schedule;
        this.updatedAt = LocalDateTime.now();
    }

    public void update(Integer generation, RecruitStatus status, LocalDate startDate, LocalDate endDate,
                        LocalDateTime resultAnnounceAt, Integer capacity, String schedule) {
        this.generation = generation;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.resultAnnounceAt = resultAnnounceAt;
        this.capacity = capacity;
        this.schedule = schedule;
        this.updatedAt = LocalDateTime.now();
    }
}
