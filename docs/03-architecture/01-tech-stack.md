# 기술 스택

> **📝 수정 사항**: 백엔드를 Node.js에서 **Spring Boot**로, 데이터베이스를 PostgreSQL에서 **MySQL**로 변경했습니다.

## 아키텍처 개요

- **프론트엔드**: Next.js (이 레포의 `/frontend` 디렉토리)
- **백엔드**: Spring Boot (이 레포의 루트/src)
- **데이터베이스**: MySQL
- **통신**: REST API
- **프로젝트 구조**: 모노레포 (Monorepo)

---

## 프론트엔드

### 핵심 프레임워크
- **React 18+** - UI 라이브러리
- **Next.js 14+** - React 프레임워크 (SSR, SSG)
- **TypeScript** - 타입 안정성

### 상태 관리
- **Redux Toolkit** 또는 **Zustand** - 전역 상태 관리
- **React Query (TanStack Query)** - 서버 상태 관리

### UI/스타일링
- **Tailwind CSS** - 유틸리티 CSS 프레임워크
- **shadcn/ui** 또는 **Ant Design** - UI 컴포넌트 라이브러리
- **Framer Motion** - 애니메이션

### 폼 관리
- **React Hook Form** - 폼 상태 관리
- **Zod** - 스키마 검증

### 비디오 플레이어
- **Video.js** 또는 **Plyr** - 커스터마이징 가능한 비디오 플레이어
- **HLS.js** - HLS 스트리밍 지원

## 백엔드

### 서버 프레임워크
- **Java 17+** - 프로그래밍 언어
- **Spring Boot 3.x** - 백엔드 프레임워크
- **Spring Web** - REST API 개발
- **Spring Data JPA** - 데이터 접근 계층

### 데이터베이스
- **MySQL 8.0** - 주 데이터베이스 (관계형) ~~PostgreSQL~~
- **Redis** (Phase 2) - 캐싱 및 세션 관리

### ORM
- **Hibernate** - JPA 구현체
- **Spring Data JPA** - 리포지토리 추상화

### 인증/보안
- **Spring Security** - 보안 프레임워크
- **JWT** - 토큰 기반 인증
- **BCrypt** - 비밀번호 해싱
- **OAuth 2.0** - 소셜 로그인 (Spring Security OAuth2)

### API
- **REST API** - 기본 API 구조
- **Spring REST Docs** 또는 **Swagger/OpenAPI** - API 문서화

## 인프라 및 DevOps

### 클라우드 플랫폼
- **AWS** 또는 **Google Cloud Platform**
  - EC2/Compute Engine - 서버
  - S3/Cloud Storage - 파일 저장
  - RDS/Cloud SQL - 관리형 데이터베이스
  - CloudFront/Cloud CDN - CDN

### 미디어 처리
- **AWS MediaConvert** 또는 **FFmpeg** - 동영상 트랜스코딩
- **AWS S3** + **CloudFront** - 동영상 스트리밍

### 컨테이너 및 오케스트레이션
- **Docker** - 컨테이너화
- **Kubernetes** 또는 **AWS ECS** - 오케스트레이션 (스케일링 시)

### CI/CD
- **GitHub Actions** - 자동화된 빌드 및 배포
- **Vercel** 또는 **AWS Amplify** - 프론트엔드 배포
- **AWS CodeDeploy** - 백엔드 배포

### 모니터링 및 로깅
- **Sentry** - 에러 트래킹
- **Google Analytics** 또는 **Mixpanel** - 사용자 분석
- **Winston** 또는 **Pino** - 로깅
- **Prometheus + Grafana** - 모니터링

## 결제

- **Stripe** - 국제 결제
- **토스페이먼츠** - 한국 결제
- **Iamport (아임포트)** - 결제 통합 솔루션

## 개발 도구

### 버전 관리
- **Git** - 버전 관리
- **GitHub** - 코드 저장소

### 코드 품질
- **ESLint** - 린팅
- **Prettier** - 코드 포맷팅
- **Husky** - Git hooks
- **Jest** - 단위 테스트
- **React Testing Library** - 컴포넌트 테스트
- **Playwright** 또는 **Cypress** - E2E 테스트

### 문서화
- **Swagger/OpenAPI** - API 문서화
- **Storybook** - 컴포넌트 문서화

## 커뮤니케이션 및 협업

- **Slack** 또는 **Discord** - 팀 커뮤니케이션
- **Notion** 또는 **Confluence** - 문서 관리
- **Jira** 또는 **Linear** - 프로젝트 관리
- **Figma** - 디자인

## 선택 기준

### 우선순위
1. **개발 생산성** - 빠른 개발과 유지보수
2. **확장성** - 사용자 증가에 대응
3. **안정성** - 서비스 신뢰성
4. **비용 효율** - 초기 투자 최소화

### 단계별 접근
- **MVP 단계 (Phase 1)**:
  - Backend: Spring Boot + MySQL + AWS S3
  - Frontend: Next.js (모노레포)
- **성장 단계 (Phase 2)**: Redis 캐싱 추가, CDN 적용
- **확장 단계 (Phase 3-4)**: 마이크로서비스, Kubernetes, 글로벌 확장

---

## 모노레포 프로젝트 구조

```
LetsLearn/                   # 모노레포 루트
├── src/                     # Spring Boot 백엔드
│   ├── main/
│   │   ├── java/
│   │   │   └── com/letslearn/
│   │   │       ├── config/          # Spring 설정
│   │   │       ├── controller/      # REST API 컨트롤러
│   │   │       ├── service/         # 비즈니스 로직
│   │   │       ├── repository/      # 데이터 접근
│   │   │       ├── domain/          # 엔티티
│   │   │       └── dto/             # 데이터 전송 객체
│   │   └── resources/
│   │       ├── application.yaml     # 애플리케이션 설정
│   │       └── db/migration/        # 데이터베이스 마이그레이션
│   └── test/                # 테스트 코드
├── frontend/                # Next.js 프론트엔드
│   ├── app/                 # Next.js App Router
│   ├── components/          # React 컴포넌트
│   ├── lib/                 # 유틸리티 함수
│   ├── hooks/               # Custom Hooks
│   ├── public/              # 정적 파일
│   ├── package.json
│   └── next.config.js
├── docs/                    # 프로젝트 문서
├── pom.xml                  # Maven 의존성
└── README.md
```

### 개발 서버 실행

**백엔드 (포트 8081)**:
```bash
./mvnw spring-boot:run
```

**프론트엔드 (포트 3000)**:
```bash
cd frontend
npm run dev
```
