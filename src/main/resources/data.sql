INSERT INTO projects (project_id, name, description, thumbnail_url, generation, github_url, service_url, view_count, created_at, updated_at) VALUES
 (1, 'UNIS 홈페이지', 'UNIS 8기 공식 홈페이지 리뉴얼 프로젝트입니다.', 'https://cdn.unis-ewha.com/thumb1.png', 8, 'https://github.com/unis/unis-web', 'https://unis-ewha.com', 0, NOW(), NOW()),
 (2, 'AI 학습 플래너', 'AI 기반 학습 스케줄링 서비스입니다.', 'https://cdn.unis-ewha.com/thumb2.png', 7, 'https://github.com/unis/ai-planner', 'https://ai-planner.unis-ewha.com', 12, NOW(), NOW());

INSERT INTO project_tech_stacks (tech_stack_id, project_id, tech_name) VALUES
 (1, 1, 'React'),
 (2, 1, 'Spring Boot'),
 (3, 1, 'MySQL'),
 (4, 2, 'Python'),
 (5, 2, 'FastAPI'),
 (6, 2, 'React');

INSERT INTO project_members (project_member_id, project_id, part, member_name) VALUES
 (1, 1, 'PLANNING', '구아연'),
 (2, 1, 'DESIGN', '정혜윤'),
 (3, 1, 'FRONTEND', '박보현'),
 (4, 1, 'BACKEND', '정유진'),
 (5, 2, 'PLANNING', '홍길동'),
 (6, 2, 'BACKEND', '김철수');

INSERT INTO archives (archive_id, generation, title, description, status, sort_order, created_at, updated_at) VALUES
 (1, '8기', '2026년 1학기 · 활동 중', '아이디어톤 · 산학협력 프로젝트 진행 중', 'IN_PROGRESS', 1, NOW(), NOW()),
 (2, '7기', '2025년 2학기 · 활동 종료', '해커톤 참가 · 사내 인턴십 공모전', 'COMPLETED', 2, NOW(), NOW()),
 (3, '6기', '2025년 1학기 · 활동 종료', 'MT · 데모데이 진행', 'COMPLETED', 3, NOW(), NOW());

INSERT INTO testimonials (testimonial_id, name, role, content, sort_order, created_at, updated_at) VALUES
 (1, '홍길동', '프론트엔드 개발자', 'UNIS에서 많이 성장했습니다.', 1, NOW(), NOW()),
 (2, '김철수', '백엔드 개발자', '실전 프로젝트 경험을 쌓을 수 있었습니다.', 2, NOW(), NOW());

INSERT INTO core_values (core_value_id, title, description, sort_order, created_at, updated_at) VALUES
 (1, 'UNIque (독보성)', '이화여대 유일의 실전 창업 IT 학회입니다.', 1, NOW(), NOW()),
 (2, 'UNIson (협력)', '기획/디자인/개발이 함께 프로젝트를 완성합니다.', 2, NOW(), NOW()),
 (3, 'Start Small, Think Big', '작게 시작해서 크게 성장합니다.', 3, NOW(), NOW());

INSERT INTO part_infos (part_info_id, name, description, sort_order, created_at, updated_at) VALUES
 (1, '기획', '사용자의 문제를 발견하고 서비스를 설계합니다.', 1, NOW(), NOW()),
 (2, '디자인', '사용자 경험을 시각적으로 구현합니다.', 2, NOW(), NOW()),
 (3, '프론트엔드', '사용자와 맞닿는 화면을 구현합니다.', 3, NOW(), NOW()),
 (4, '백엔드', '서비스의 핵심 로직과 데이터를 다룹니다.', 4, NOW(), NOW());

INSERT INTO part_tags (part_tag_id, part_info_id, tag_name) VALUES
 (1, 1, '프로덕트 기획'),
 (2, 1, '프로젝트 관리'),
 (3, 2, 'UI/UX'),
 (4, 2, '브랜딩'),
 (5, 3, 'React'),
 (6, 3, 'TypeScript'),
 (7, 4, 'Spring Boot'),
 (8, 4, 'MySQL');

INSERT INTO about_photos (photo_id, label, image_url, sort_order, created_at, updated_at) VALUES
 (1, 'OT & 팀빌딩', 'https://cdn.unis-ewha.com/photo1.png', 1, NOW(), NOW()),
 (2, '아이디어톤', 'https://cdn.unis-ewha.com/photo2.png', 2, NOW(), NOW()),
 (3, 'MT', 'https://cdn.unis-ewha.com/photo3.png', 3, NOW(), NOW()),
 (4, '데모데이', 'https://cdn.unis-ewha.com/photo4.png', 4, NOW(), NOW());

INSERT INTO faqs (faq_id, question, answer, sort_order, created_at, updated_at) VALUES
 (1, '활동 시간은 언제인가요?', '매주 토요일 오후 2시에 정규 세션이 진행됩니다.', 1, NOW(), NOW()),
 (2, '창업 경험이 없어도 지원할 수 있나요?', '네, 누구든지 지원 가능합니다.', 2, NOW(), NOW());

INSERT INTO activity_programs (program_id, title, description, image_url, sort_order, created_at, updated_at) VALUES
 (1, '정규 세션', '창업과 비즈니스에 대한 정규 커리큘럼을 진행합니다.', 'https://cdn.unis-ewha.com/program1.png', 1, NOW(), NOW()),
 (2, '네트워킹', '타 학회 및 현업 멘토와의 네트워킹 자리를 마련합니다.', 'https://cdn.unis-ewha.com/program2.png', 2, NOW(), NOW()),
 (3, '직무 스터디', '파트별 직무 역량을 키우는 스터디를 운영합니다.', 'https://cdn.unis-ewha.com/program3.png', 3, NOW(), NOW());

INSERT INTO activity_curriculum (curriculum_id, step_order, title, description, created_at, updated_at) VALUES
 (1, 1, 'OT & 팀빌딩', 'UNIS의 활동 방향과 학기 커리큘럼을 소개합니다.', NOW(), NOW()),
 (2, 2, '아이디어톤', '서비스 아이디어를 발굴하고 구체화합니다.', NOW(), NOW()),
 (3, 3, '산학협력 프로젝트', '실제 기업과 함께 프로젝트를 진행합니다.', NOW(), NOW()),
 (4, 4, '데모데이', '한 학기 동안의 프로젝트 결과물을 발표합니다.', NOW(), NOW());
