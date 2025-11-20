# 데이터베이스 스키마 설계

## ERD 개요

```
Users ──< Enrollments >── Courses
  │                         │
  │                         └──< CourseContents
  │                         │
  │                         └──< Reviews
  │                         │
  │                         └──< QnA
  │
  └──< Payments
  │
  └──< UserProfiles
  │
  └──< InstructorProfiles
```

## 주요 테이블

### 1. Users (사용자)

```sql
CREATE TABLE users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email VARCHAR(255) UNIQUE NOT NULL,
  password_hash VARCHAR(255), -- null if OAuth user
  username VARCHAR(100) UNIQUE NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'student', -- student, instructor, admin
  email_verified BOOLEAN DEFAULT false,
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_login_at TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
```

### 2. UserProfiles (사용자 프로필)

```sql
CREATE TABLE user_profiles (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id) ON DELETE CASCADE,
  full_name VARCHAR(255),
  avatar_url VARCHAR(500),
  bio TEXT,
  phone VARCHAR(20),
  language VARCHAR(10) DEFAULT 'ko',
  timezone VARCHAR(50) DEFAULT 'Asia/Seoul',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE(user_id)
);
```

### 3. InstructorProfiles (강사 프로필)

```sql
CREATE TABLE instructor_profiles (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id) ON DELETE CASCADE,
  headline VARCHAR(255), -- e.g., "Senior Full-stack Developer"
  description TEXT,
  website_url VARCHAR(500),
  github_url VARCHAR(500),
  linkedin_url VARCHAR(500),
  total_students INTEGER DEFAULT 0,
  total_reviews INTEGER DEFAULT 0,
  average_rating DECIMAL(3,2) DEFAULT 0,
  commission_rate DECIMAL(5,2) DEFAULT 30.00, -- 수수료율 (%)
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE(user_id)
);
```

### 4. Categories (카테고리)

```sql
CREATE TABLE categories (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(100) NOT NULL,
  slug VARCHAR(100) UNIQUE NOT NULL,
  description TEXT,
  parent_id UUID REFERENCES categories(id) ON DELETE SET NULL,
  icon VARCHAR(100),
  order_index INTEGER DEFAULT 0,
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_categories_parent ON categories(parent_id);
CREATE INDEX idx_categories_slug ON categories(slug);
```

### 5. Courses (강의)

```sql
CREATE TABLE courses (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  instructor_id UUID REFERENCES users(id) ON DELETE CASCADE,
  category_id UUID REFERENCES categories(id) ON DELETE SET NULL,
  title VARCHAR(255) NOT NULL,
  slug VARCHAR(255) UNIQUE NOT NULL,
  subtitle VARCHAR(500),
  description TEXT,
  what_you_will_learn TEXT[], -- 학습 목표 배열
  requirements TEXT[], -- 선수 지식
  target_audience TEXT[], -- 대상 수강생

  -- 미디어
  thumbnail_url VARCHAR(500),
  preview_video_url VARCHAR(500),

  -- 가격
  price DECIMAL(10,2) NOT NULL DEFAULT 0,
  original_price DECIMAL(10,2), -- 할인 전 가격
  currency VARCHAR(3) DEFAULT 'KRW',

  -- 난이도
  level VARCHAR(20) DEFAULT 'beginner', -- beginner, intermediate, advanced

  -- 상태
  status VARCHAR(20) DEFAULT 'draft', -- draft, under_review, published, archived
  is_published BOOLEAN DEFAULT false,
  published_at TIMESTAMP,

  -- 통계
  total_duration INTEGER DEFAULT 0, -- 총 강의 시간 (분)
  total_lectures INTEGER DEFAULT 0,
  enrollment_count INTEGER DEFAULT 0,
  view_count INTEGER DEFAULT 0,

  -- 평점
  average_rating DECIMAL(3,2) DEFAULT 0,
  review_count INTEGER DEFAULT 0,

  -- 메타데이터
  language VARCHAR(10) DEFAULT 'ko',
  has_certificate BOOLEAN DEFAULT true,
  has_lifetime_access BOOLEAN DEFAULT true,

  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_courses_instructor ON courses(instructor_id);
CREATE INDEX idx_courses_category ON courses(category_id);
CREATE INDEX idx_courses_status ON courses(status);
CREATE INDEX idx_courses_published ON courses(is_published);
```

### 6. CourseSections (강의 섹션)

```sql
CREATE TABLE course_sections (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  course_id UUID REFERENCES courses(id) ON DELETE CASCADE,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  order_index INTEGER NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_sections_course ON course_sections(course_id);
```

### 7. CourseLectures (강의 레슨/강)

```sql
CREATE TABLE course_lectures (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  section_id UUID REFERENCES course_sections(id) ON DELETE CASCADE,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  content_type VARCHAR(20) NOT NULL, -- video, article, quiz

  -- 비디오 정보
  video_url VARCHAR(500),
  video_duration INTEGER, -- 초 단위
  video_quality VARCHAR(50)[], -- ['360p', '720p', '1080p']

  -- 자료
  attachments JSONB, -- [{name, url, size, type}]

  -- 설정
  is_preview BOOLEAN DEFAULT false, -- 미리보기 가능 여부
  order_index INTEGER NOT NULL,

  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_lectures_section ON course_lectures(section_id);
```

### 8. Enrollments (수강)

```sql
CREATE TABLE enrollments (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id) ON DELETE CASCADE,
  course_id UUID REFERENCES courses(id) ON DELETE CASCADE,

  -- 진도
  progress DECIMAL(5,2) DEFAULT 0, -- 0-100%
  completed_lectures INTEGER DEFAULT 0,
  last_accessed_lecture_id UUID REFERENCES course_lectures(id),
  last_accessed_at TIMESTAMP,

  -- 완료
  is_completed BOOLEAN DEFAULT false,
  completed_at TIMESTAMP,

  -- 수료증
  certificate_url VARCHAR(500),
  certificate_issued_at TIMESTAMP,

  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  UNIQUE(user_id, course_id)
);

CREATE INDEX idx_enrollments_user ON enrollments(user_id);
CREATE INDEX idx_enrollments_course ON enrollments(course_id);
```

### 9. LectureProgress (강의 진도)

```sql
CREATE TABLE lecture_progress (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  enrollment_id UUID REFERENCES enrollments(id) ON DELETE CASCADE,
  lecture_id UUID REFERENCES course_lectures(id) ON DELETE CASCADE,

  -- 진도
  is_completed BOOLEAN DEFAULT false,
  completed_at TIMESTAMP,
  video_progress INTEGER DEFAULT 0, -- 초 단위
  last_position INTEGER DEFAULT 0, -- 마지막 시청 위치

  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  UNIQUE(enrollment_id, lecture_id)
);

CREATE INDEX idx_lecture_progress_enrollment ON lecture_progress(enrollment_id);
```

### 10. Reviews (수강평)

```sql
CREATE TABLE reviews (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  course_id UUID REFERENCES courses(id) ON DELETE CASCADE,
  user_id UUID REFERENCES users(id) ON DELETE CASCADE,
  rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
  title VARCHAR(255),
  content TEXT,
  helpful_count INTEGER DEFAULT 0,
  instructor_reply TEXT,
  instructor_replied_at TIMESTAMP,
  is_public BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  UNIQUE(course_id, user_id)
);

CREATE INDEX idx_reviews_course ON reviews(course_id);
CREATE INDEX idx_reviews_user ON reviews(user_id);
CREATE INDEX idx_reviews_rating ON reviews(rating);
```

### 11. QnA (질문 & 답변)

```sql
CREATE TABLE qna_questions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  course_id UUID REFERENCES courses(id) ON DELETE CASCADE,
  lecture_id UUID REFERENCES course_lectures(id) ON DELETE SET NULL,
  user_id UUID REFERENCES users(id) ON DELETE CASCADE,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  is_resolved BOOLEAN DEFAULT false,
  upvote_count INTEGER DEFAULT 0,
  answer_count INTEGER DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE qna_answers (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  question_id UUID REFERENCES qna_questions(id) ON DELETE CASCADE,
  user_id UUID REFERENCES users(id) ON DELETE CASCADE,
  content TEXT NOT NULL,
  is_best_answer BOOLEAN DEFAULT false,
  upvote_count INTEGER DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_qna_questions_course ON qna_questions(course_id);
CREATE INDEX idx_qna_questions_user ON qna_questions(user_id);
CREATE INDEX idx_qna_answers_question ON qna_answers(question_id);
```

### 12. Payments (결제)

```sql
CREATE TABLE payments (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id) ON DELETE CASCADE,
  course_id UUID REFERENCES courses(id) ON DELETE SET NULL,

  -- 결제 정보
  amount DECIMAL(10,2) NOT NULL,
  currency VARCHAR(3) DEFAULT 'KRW',
  payment_method VARCHAR(50), -- card, bank_transfer, etc.
  payment_provider VARCHAR(50), -- stripe, tosspayments, etc.
  transaction_id VARCHAR(255) UNIQUE,

  -- 할인
  coupon_code VARCHAR(50),
  discount_amount DECIMAL(10,2) DEFAULT 0,

  -- 상태
  status VARCHAR(20) NOT NULL, -- pending, completed, failed, refunded

  -- 환불
  refunded_at TIMESTAMP,
  refund_amount DECIMAL(10,2),
  refund_reason TEXT,

  -- 메타데이터
  metadata JSONB,

  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_payments_user ON payments(user_id);
CREATE INDEX idx_payments_course ON payments(course_id);
CREATE INDEX idx_payments_status ON payments(status);
CREATE INDEX idx_payments_transaction ON payments(transaction_id);
```

### 13. Coupons (쿠폰)

```sql
CREATE TABLE coupons (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  code VARCHAR(50) UNIQUE NOT NULL,
  description VARCHAR(255),

  -- 할인
  discount_type VARCHAR(20) NOT NULL, -- percentage, fixed
  discount_value DECIMAL(10,2) NOT NULL,
  max_discount_amount DECIMAL(10,2),

  -- 적용 범위
  applicable_to VARCHAR(20) DEFAULT 'all', -- all, course, category
  course_id UUID REFERENCES courses(id) ON DELETE CASCADE,
  category_id UUID REFERENCES categories(id) ON DELETE CASCADE,

  -- 사용 제한
  usage_limit INTEGER,
  usage_count INTEGER DEFAULT 0,
  per_user_limit INTEGER DEFAULT 1,

  -- 유효 기간
  valid_from TIMESTAMP NOT NULL,
  valid_until TIMESTAMP NOT NULL,

  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_coupons_code ON coupons(code);
```

### 14. Notifications (알림)

```sql
CREATE TABLE notifications (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id) ON DELETE CASCADE,
  type VARCHAR(50) NOT NULL, -- new_answer, course_update, promotion, etc.
  title VARCHAR(255) NOT NULL,
  content TEXT,
  link_url VARCHAR(500),
  is_read BOOLEAN DEFAULT false,
  read_at TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notifications_user ON notifications(user_id);
CREATE INDEX idx_notifications_read ON notifications(is_read);
```

## 관계 요약

1. **Users - Courses**: 1:N (한 강사가 여러 강의 생성)
2. **Courses - Enrollments**: 1:N (한 강의에 여러 수강생)
3. **Users - Enrollments**: 1:N (한 사용자가 여러 강의 수강)
4. **Courses - Sections**: 1:N (한 강의에 여러 섹션)
5. **Sections - Lectures**: 1:N (한 섹션에 여러 레슨)
6. **Courses - Reviews**: 1:N (한 강의에 여러 수강평)
7. **Courses - QnA**: 1:N (한 강의에 여러 질문)

## 인덱스 전략

- Primary Key: 모든 테이블에 UUID
- Foreign Key: 관계 조회 성능 향상
- 검색 필드: email, slug, status 등
- 복합 인덱스: (user_id, course_id) 등

## 데이터 무결성

- CASCADE: 사용자 삭제 시 관련 데이터 삭제
- SET NULL: 강의 삭제 시 결제 기록은 유지
- CHECK 제약: 평점 범위, 금액 양수 등
