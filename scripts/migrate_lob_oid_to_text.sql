-- ============================================================
-- @Lob(String) -> oid 매핑 문제 수정 마이그레이션
--
-- 배경:
--   Hibernate에서 `@Lob private String ...` 필드는 PostgreSQL 배포 환경에서
--   기본적으로 Large Object(oid 참조 타입) 컬럼으로 매핑된다. oid 컬럼은
--   LIKE(~~) 등 문자열 연산자를 지원하지 않아 검색 쿼리가
--   "operator does not exist: oid ~~ text" 에러로 실패한다.
--
--   애플리케이션 코드는 해당 필드들을 `@JdbcTypeCode(SqlTypes.LONGVARCHAR)`로
--   교체했지만, Hibernate의 ddl-auto=update는 기존에 이미 생성된 컬럼의
--   타입을 되돌려서 바꿔주지 않는다. 이미 운영 DB에 oid로 생성되어 있는
--   컬럼은 이 스크립트로 직접 text로 변환해야 한다.
--
-- 대상: 13개 엔티티, 17개 컬럼
--   core_values.description, part_infos.description,
--   faqs.question, faqs.answer,
--   activity_programs.description, activity_curriculum.description,
--   applications.self_introduction, applications.motivation,
--   applications.project_experience, applications.conflict_experience,
--   community_posts.content, comments.content, recruitments.content,
--   projects.description, page_contents.content_json,
--   archives.description, testimonials.content
--
-- 동작 방식:
--   각 컬럼마다 현재 타입이 실제로 'oid'인 경우에만 변환을 수행한다(멱등적 -
--   이미 text/varchar인 컬럼이나 재실행 시 안전하게 건너뜀). oid 컬럼에
--   저장된 값은 실제 텍스트가 아니라 Large Object 참조이므로, 단순
--   `column::text` 캐스팅은 참조 번호 문자열("16384" 등)로 깨진 값이 되어
--   버린다. 반드시 lo_get()으로 실제 바이트를 읽어 convert_from()으로
--   텍스트로 복원해야 한다. 변환 후에는 더 이상 참조되지 않는 Large
--   Object를 lo_unlink()로 정리해 pg_largeobject에 고아 데이터가
--   남지 않도록 한다.
--
-- 사전 요구사항:
--   - PostgreSQL 9.4 이상 (lo_get() 함수 필요)
--   - 이 스크립트를 실행하는 DB 롤이 대상 테이블의 소유자이거나 그에
--     준하는 권한을 가지고 있어야 함 (ALTER TABLE, large object 정리 권한)
--
-- 주의:
--   - 실행 전 반드시 백업을 확보할 것 (예: pg_dump).
--   - ALTER TABLE은 테이블에 ACCESS EXCLUSIVE 락을 거므로, 트래픽이
--     적은 시간대(배포/점검 시간)에 실행할 것을 권장한다.
--   - 컬럼 하나당 전체 테이블을 한 번 스캔하며 변환하므로, 데이터量이
--     매우 큰 테이블은 예상보다 시간이 걸릴 수 있다.
--   - 전체를 하나의 트랜잭션으로 묶어 실행한다: 중간에 실패하면 전부
--     롤백되어 일부 컬럼만 변환된 상태로 남지 않는다.
-- ============================================================

BEGIN;

-- 컬럼 하나를 oid -> text로 변환하는 공통 로직.
-- 이미 oid가 아니면 아무 것도 하지 않고 건너뛴다(멱등성).
DO $migration$
DECLARE
    v_loids oid[];
    v_count integer;
    v_targets text[][] := ARRAY[
        ['core_values', 'description'],
        ['part_infos', 'description'],
        ['faqs', 'question'],
        ['faqs', 'answer'],
        ['activity_programs', 'description'],
        ['activity_curriculum', 'description'],
        ['applications', 'self_introduction'],
        ['applications', 'motivation'],
        ['applications', 'project_experience'],
        ['applications', 'conflict_experience'],
        ['community_posts', 'content'],
        ['comments', 'content'],
        ['recruitments', 'content'],
        ['projects', 'description'],
        ['page_contents', 'content_json'],
        ['archives', 'description'],
        ['testimonials', 'content']
    ];
    v_table text;
    v_column text;
    v_current_type text;
    i integer;
BEGIN
    FOR i IN 1 .. array_length(v_targets, 1) LOOP
        v_table := v_targets[i][1];
        v_column := v_targets[i][2];

        SELECT data_type INTO v_current_type
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = v_table
          AND column_name = v_column;

        IF v_current_type IS NULL THEN
            RAISE NOTICE '% .% : 컬럼을 찾을 수 없음 (건너뜀)', v_table, v_column;
            CONTINUE;
        END IF;

        IF v_current_type <> 'oid' THEN
            RAISE NOTICE '%.%: 이미 oid가 아님 (현재 %, 건너뜀)', v_table, v_column, v_current_type;
            CONTINUE;
        END IF;

        -- 변환 후에는 컬럼에서 원래 oid 값을 더 이상 읽을 수 없으므로,
        -- lo_unlink에 쓸 목록을 ALTER 전에 미리 캡처해 둔다.
        EXECUTE format('SELECT array_agg(%I) FROM %I WHERE %I IS NOT NULL', v_column, v_table, v_column)
            INTO v_loids;

        EXECUTE format(
            'ALTER TABLE %I ALTER COLUMN %I TYPE text USING convert_from(lo_get(%I), ''UTF8'')',
            v_table, v_column, v_column
        );

        v_count := COALESCE(array_length(v_loids, 1), 0);

        IF v_loids IS NOT NULL THEN
            PERFORM lo_unlink(loid) FROM unnest(v_loids) AS loid;
        END IF;

        RAISE NOTICE '%.%: oid -> text 변환 완료 (%건, large object 정리 완료)', v_table, v_column, v_count;
    END LOOP;
END;
$migration$;

COMMIT;

-- 변환 결과 확인용: 아래 쿼리 결과에 data_type = 'oid'인 행이 하나도
-- 없어야 정상적으로 끝난 것이다.
SELECT table_name, column_name, data_type
FROM information_schema.columns
WHERE table_schema = 'public'
  AND (table_name, column_name) IN (
        ('core_values', 'description'),
        ('part_infos', 'description'),
        ('faqs', 'question'),
        ('faqs', 'answer'),
        ('activity_programs', 'description'),
        ('activity_curriculum', 'description'),
        ('applications', 'self_introduction'),
        ('applications', 'motivation'),
        ('applications', 'project_experience'),
        ('applications', 'conflict_experience'),
        ('community_posts', 'content'),
        ('comments', 'content'),
        ('recruitments', 'content'),
        ('projects', 'description'),
        ('page_contents', 'content_json'),
        ('archives', 'description'),
        ('testimonials', 'content')
  )
ORDER BY table_name, column_name;
