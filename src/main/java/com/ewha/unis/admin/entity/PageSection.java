package com.ewha.unis.admin.entity;

/**
 * ABOUT_VALUES(핵심 가치)는 core_values 테이블로 별도 관리되므로 여기 포함하지 않는다.
 * about 페이지의 공개 GET(3-1)이 core_values를 그대로 읽기 때문에,
 * PAGE_CONTENTS의 JSON으로 이원화하면 Admin이 저장해도 실제 About 페이지에 반영되지 않는다.
 */
public enum PageSection {
    HERO, ABOUT_INTRO, ABOUT_EXPERIENCE
}
