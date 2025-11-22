# Phase 1 개발 계획 (MVP)

> **목표**: 핵심 기능만 구현하여 초기 사용자 피드백 수집
> **기간**: 4-6주
> **기술 스택**: Spring Boot + MySQL + Next.js (모노레포)

---

## 1주차: 프로젝트 기반 설정

### 백엔드 설정
- [x] Spring Boot 프로젝트 초기화
- [x] MySQL 연결 설정 (포트 3307)
- [x] Spring Security 기본 설정
- [ ] 데이터베이스 스키마 설계
- [ ] JPA 엔티티 작성
  - [ ] User (사용자)
  - [ ] Course (강의)
  - [ ] Section (섹션)
  - [ ] Lesson (레슨)
  - [ ] Enrollment (수강 신청)
- [ ] Flyway 또는 Liquibase 마이그레이션 설정

### 프론트엔드 설정
- [ ] Next.js 14 프로젝트 생성 (`/frontend`)
- [ ] TypeScript 설정
- [ ] Tailwind CSS 설정
- [ ] 기본 레이아웃 구성
- [ ] API 클라이언트 설정 (Axios 또는 Fetch)

### DevOps
- [ ] Docker Compose 설정 (MySQL, Redis)
- [ ] 개발 환경 문서화
- [ ] Git 브랜치 전략 수립

---

## 2주차: 인증 시스템

### 백엔드 (Spring Boot)
- [ ] JWT 토큰 생성/검증 유틸리티
- [ ] Spring Security JWT 필터 구성
- [ ] 회원가입 API
  - `POST /api/auth/register`
  - 이메일, 비밀번호, 이름 입력
  - 비밀번호 BCrypt 해싱
- [ ] 로그인 API
  - `POST /api/auth/login`
  - JWT Access Token + Refresh Token 발급
- [ ] 토큰 갱신 API
  - `POST /api/auth/refresh`
- [ ] 내 정보 조회 API
  - `GET /api/users/me`
- [ ] 프로필 수정 API
  - `PUT /api/users/me`

### 프론트엔드 (Next.js)
- [ ] 회원가입 페이지 (`/signup`)
- [ ] 로그인 페이지 (`/login`)
- [ ] JWT 토큰 저장 (httpOnly Cookie)
- [ ] 인증 상태 관리 (Context API 또는 Zustand)
- [ ] Protected Route 구현
- [ ] 내 프로필 페이지 (`/profile`)

### 테스트
- [ ] 회원가입/로그인 단위 테스트
- [ ] JWT 토큰 검증 테스트

---

## 3주차: 강의 등록 (강사)

### 백엔드
- [ ] 강의 CRUD API
  - `POST /api/courses` - 강의 생성
  - `GET /api/courses/{id}` - 강의 상세 조회
  - `PUT /api/courses/{id}` - 강의 수정
  - `DELETE /api/courses/{id}` - 강의 삭제
- [ ] 섹션 CRUD API
  - `POST /api/courses/{courseId}/sections`
  - `PUT /api/sections/{id}`
  - `DELETE /api/sections/{id}`
- [ ] 레슨 CRUD API
  - `POST /api/sections/{sectionId}/lessons`
  - `PUT /api/lessons/{id}`
  - `DELETE /api/lessons/{id}`
- [ ] AWS S3 동영상 업로드 API
  - `POST /api/upload/video`
  - Presigned URL 생성
- [ ] 강사 권한 체크 (본인이 생성한 강의만 수정 가능)

### 프론트엔드
- [ ] 강의 생성 페이지 (`/instructor/courses/new`)
  - 강의 제목, 설명, 카테고리, 가격 입력
- [ ] 강의 수정 페이지 (`/instructor/courses/[id]/edit`)
  - 섹션 추가/수정/삭제
  - 레슨 추가/수정/삭제
  - 동영상 업로드 (AWS S3)
- [ ] 내 강의 목록 (`/instructor/courses`)
- [ ] 드래그 앤 드롭으로 섹션/레슨 순서 변경

### AWS S3 설정
- [ ] S3 버킷 생성
- [ ] IAM 사용자 생성 및 권한 설정
- [ ] Spring Boot에 AWS SDK 설정

---

## 4주차: 강의 탐색 및 수강

### 백엔드
- [ ] 강의 목록 조회 API
  - `GET /api/courses?page=0&size=20&sort=createdAt,desc`
  - 페이징 처리
- [ ] 강의 검색 API
  - `GET /api/courses/search?keyword=spring`
- [ ] 강의 상세 조회 API (public)
  - `GET /api/courses/{id}`
  - 섹션 및 레슨 목록 포함
- [ ] 수강 신청 API
  - `POST /api/enrollments/{courseId}`
- [ ] 내 수강 목록 조회
  - `GET /api/enrollments/my`
- [ ] 진도율 저장 API
  - `PUT /api/enrollments/{enrollmentId}/progress`
  - 현재 레슨 ID, 진행 시간 저장

### 프론트엔드
- [ ] 홈 페이지 (`/`)
  - 인기 강의 목록
  - 카테고리별 강의
- [ ] 강의 목록 페이지 (`/courses`)
  - 검색 기능
  - 필터링 (카테고리, 가격)
  - 페이지네이션
- [ ] 강의 상세 페이지 (`/courses/[id]`)
  - 강의 정보, 커리큘럼
  - 수강 신청 버튼
- [ ] 비디오 플레이어 (`/learn/[courseId]/[lessonId]`)
  - Video.js 또는 Plyr 사용
  - 진도율 자동 저장
  - 이어보기 기능
- [ ] 내 강의실 (`/my-courses`)
  - 수강 중인 강의 목록
  - 진도율 표시

---

## 5주차: 결제 시스템

### 백엔드
- [ ] 결제 준비 API
  - `POST /api/payments/prepare`
  - 주문 정보 생성 (orderId, amount)
- [ ] 토스페이먼츠 승인 API
  - `POST /api/payments/confirm`
  - 결제 승인 후 수강 권한 부여
- [ ] 결제 내역 조회 API
  - `GET /api/payments/my`

### 토스페이먼츠 연동
- [ ] 토스페이먼츠 개발자 계정 생성
- [ ] 테스트 API 키 발급
- [ ] Spring Boot에 토스페이먼츠 클라이언트 설정

### 프론트엔드
- [ ] 결제 페이지 (`/checkout/[courseId]`)
  - 강의 정보, 가격 표시
  - 토스페이먼츠 결제 위젯 연동
- [ ] 결제 성공/실패 페이지
  - `/checkout/success`
  - `/checkout/fail`
- [ ] 결제 내역 페이지 (`/payments`)

---

## 6주차: 테스트 및 배포 준비

### 테스트
- [ ] 단위 테스트 작성
  - Service 계층 테스트
  - Repository 테스트
- [ ] 통합 테스트
  - API 엔드포인트 테스트
  - 인증/인가 테스트
- [ ] E2E 테스트 (Playwright)
  - 회원가입 → 로그인 → 강의 수강 플로우

### 성능 최적화
- [ ] Database 인덱스 추가
  - User: email (unique)
  - Course: createdAt, category
  - Enrollment: userId, courseId
- [ ] N+1 문제 해결 (Fetch Join)
- [ ] API 응답 시간 측정

### 배포 준비
- [ ] 환경 변수 분리 (dev, prod)
- [ ] Docker 이미지 빌드
  - Backend Dockerfile
  - Frontend Dockerfile
- [ ] Docker Compose 프로덕션 설정
- [ ] CI/CD 파이프라인 설정 (GitHub Actions)

### 문서화
- [ ] API 문서 작성 (Swagger/OpenAPI)
- [ ] 개발 환경 설정 가이드
- [ ] 배포 가이드

---

## 성공 지표 (Phase 1 완료 기준)

- [ ] 회원가입/로그인이 정상 작동
- [ ] 강사가 강의를 등록하고 동영상 업로드 가능
- [ ] 학생이 강의를 검색하고 상세 조회 가능
- [ ] 결제 후 강의 수강 가능
- [ ] 비디오 플레이어에서 강의 시청 및 진도율 저장
- [ ] 모든 핵심 API가 정상 작동
- [ ] 프론트엔드와 백엔드 통신 정상

---

## 우선순위

### Must Have (필수)
1. 인증 시스템
2. 강의 CRUD
3. 강의 수강
4. 비디오 플레이어
5. 결제 시스템

### Should Have (권장)
1. 검색 기능
2. 진도율 저장
3. 이어보기

### Nice to Have (선택)
1. 필터링 고도화
2. 추천 강의
3. 수강평 (Phase 2로 이동 가능)

---

## 다음 단계: Phase 2

Phase 1 완료 후:
- [ ] 사용자 피드백 수집
- [ ] 버그 수정 및 성능 개선
- [ ] Phase 2 기능 계획
  - 소셜 로그인
  - Q&A 게시판
  - 수강평 시스템
  - 강사 대시보드
