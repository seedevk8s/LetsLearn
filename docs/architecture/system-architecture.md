# 시스템 아키텍처

## 전체 시스템 구조

```
┌─────────────────────────────────────────────────────────────┐
│                         Client Layer                         │
├─────────────────────────────────────────────────────────────┤
│  Web Application (Next.js)                                   │
│  - Student Portal                                            │
│  - Instructor Dashboard                                      │
│  - Admin Panel                                               │
└─────────────────────────────────────────────────────────────┘
                            ↓ HTTPS
┌─────────────────────────────────────────────────────────────┐
│                      API Gateway / CDN                       │
│                      (CloudFront/Nginx)                      │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                    Application Layer                         │
├─────────────────────────────────────────────────────────────┤
│  API Server (Node.js + Express/NestJS)                      │
│  ┌──────────────┬──────────────┬──────────────┐            │
│  │ Auth Service │ Course Svc   │ Payment Svc  │            │
│  ├──────────────┼──────────────┼──────────────┤            │
│  │ User Service │ Media Svc    │ Notification │            │
│  └──────────────┴──────────────┴──────────────┘            │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                      Data Layer                              │
├──────────────────┬──────────────────┬──────────────────────┤
│   PostgreSQL     │      Redis       │   File Storage       │
│  (Main DB)       │   (Cache/Queue)  │   (S3/GCS)          │
│  - Users         │   - Sessions     │   - Videos          │
│  - Courses       │   - Cache        │   - Images          │
│  - Enrollments   │   - Job Queue    │   - Documents       │
│  - Payments      │                  │                     │
└──────────────────┴──────────────────┴──────────────────────┘
```

## 주요 컴포넌트

### 1. 프론트엔드 계층

#### Web Application (Next.js)
- **SSR/SSG**: SEO 최적화 및 초기 로딩 성능 향상
- **SPA**: 동적인 사용자 경험
- **Code Splitting**: 페이지별 번들 분리로 성능 최적화

**주요 페이지**:
- `/` - 홈페이지
- `/courses` - 강의 목록
- `/courses/[id]` - 강의 상세
- `/learn/[id]` - 수강 페이지
- `/instructor/dashboard` - 강사 대시보드
- `/my-courses` - 내 강의실
- `/admin` - 관리자 패널

### 2. API 계층

#### API Server
모놀리식 구조로 시작하여, 필요시 마이크로서비스로 전환

**주요 서비스 모듈**:

1. **인증 서비스 (Auth Service)**
   - 회원가입/로그인
   - JWT 토큰 발급/검증
   - OAuth 통합

2. **사용자 서비스 (User Service)**
   - 프로필 관리
   - 권한 관리
   - 사용자 설정

3. **강의 서비스 (Course Service)**
   - 강의 CRUD
   - 카테고리 관리
   - 검색 및 필터링
   - 커리큘럼 관리

4. **수강 서비스 (Enrollment Service)**
   - 수강 신청
   - 진도율 관리
   - 수료증 발급

5. **미디어 서비스 (Media Service)**
   - 동영상 업로드
   - 트랜스코딩
   - 스트리밍 URL 생성

6. **결제 서비스 (Payment Service)**
   - 결제 처리
   - 환불 처리
   - 정산 관리

7. **알림 서비스 (Notification Service)**
   - 이메일 발송
   - 푸시 알림
   - 인앱 알림

8. **커뮤니티 서비스 (Community Service)**
   - Q&A 관리
   - 수강평 관리
   - 댓글 시스템

### 3. 데이터 계층

#### PostgreSQL (주 데이터베이스)
- 사용자 데이터
- 강의 메타데이터
- 거래 데이터
- 관계형 데이터

#### Redis
- 세션 관리
- API 응답 캐싱
- 실시간 데이터 (시청자 수 등)
- Job Queue (이메일, 동영상 처리)

#### S3/Cloud Storage
- 원본 동영상
- 트랜스코딩된 동영상
- 이미지 파일
- 강의 자료

## 데이터 흐름

### 강의 수강 플로우

```
1. 사용자 로그인
   Client → API (Auth) → PostgreSQL
   ↓ JWT 토큰 발급

2. 강의 목록 조회
   Client → API (Course) → Redis (Cache Hit?)
   ↓ Cache Miss
   → PostgreSQL → Redis (캐싱)

3. 강의 구매
   Client → API (Payment) → 결제 게이트웨이
   ↓ 성공
   → PostgreSQL (거래 기록)
   → Notification Service (영수증 이메일)

4. 강의 시청
   Client → API (Enrollment) → PostgreSQL (권한 확인)
   ↓ 인증 성공
   → Media Service → S3/CloudFront (동영상 스트리밍)

5. 진도율 저장
   Client → API (Enrollment) → PostgreSQL
```

### 강의 업로드 플로우

```
1. 강사 동영상 업로드
   Client → API (Media) → S3 (원본 저장)
   ↓
   → Job Queue (Redis)

2. 비동기 처리
   Worker → Redis Queue → FFmpeg/MediaConvert
   ↓ 트랜스코딩 (여러 화질)
   → S3 (HLS 파일 생성)

3. 완료 알림
   Worker → Notification Service
   → 강사에게 이메일/푸시 알림
```

## 확장성 고려사항

### 수평 확장 (Horizontal Scaling)
- **API 서버**: 로드 밸런서를 통한 다중 인스턴스
- **데이터베이스**: Read Replica 구성
- **Redis**: Redis Cluster

### 캐싱 전략
- **CDN**: 정적 자산 및 동영상 캐싱
- **Redis**: API 응답, 세션, 강의 목록
- **Browser Cache**: 클라이언트 캐싱

### 비동기 처리
- **Job Queue**: 동영상 처리, 이메일 발송
- **Event-Driven**: 주요 이벤트 발생 시 알림

## 보안 고려사항

### 인증 및 인가
- JWT 기반 인증
- Refresh Token 순환
- RBAC (Role-Based Access Control)

### 데이터 보호
- HTTPS 통신
- 데이터 암호화 (at-rest, in-transit)
- SQL Injection 방지 (ORM 사용)
- XSS 방지

### API 보안
- Rate Limiting
- CORS 설정
- API Key 관리

### 동영상 보호
- Signed URL (S3 Pre-signed URL)
- DRM (선택사항)
- HLS 암호화

## 성능 최적화

### 프론트엔드
- Code Splitting
- Lazy Loading
- Image Optimization (Next.js Image)
- Service Worker (오프라인 지원)

### 백엔드
- Database Indexing
- Query Optimization
- N+1 문제 해결
- Connection Pooling

### 미디어
- 동영상 압축
- Adaptive Bitrate Streaming (HLS)
- CDN 활용

## 모니터링 및 로깅

### 애플리케이션 모니터링
- **Sentry**: 에러 추적
- **New Relic/DataDog**: APM
- **Prometheus + Grafana**: 메트릭

### 로그 관리
- **Winston/Pino**: 구조화된 로깅
- **ELK Stack**: 로그 수집 및 분석

### 알림
- 장애 발생 시 Slack/PagerDuty 알림
- 성능 저하 모니터링
