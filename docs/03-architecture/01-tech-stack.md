# 기술 스택

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
- **Node.js** - 런타임
- **Express.js** 또는 **NestJS** - 백엔드 프레임워크
- **TypeScript** - 타입 안정성

### 데이터베이스
- **PostgreSQL** - 주 데이터베이스 (관계형)
- **Redis** - 캐싱 및 세션 관리
- **MongoDB** (선택사항) - 로그 및 비정형 데이터

### ORM/쿼리 빌더
- **Prisma** 또는 **TypeORM** - ORM
- **Drizzle ORM** (대안)

### 인증/보안
- **JWT** - 토큰 기반 인증
- **Passport.js** - 인증 미들웨어
- **bcrypt** - 비밀번호 해싱
- **OAuth 2.0** - 소셜 로그인

### API
- **REST API** - 기본 API 구조
- **GraphQL** (선택사항) - 유연한 데이터 쿼리

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
- **MVP 단계**: Next.js + PostgreSQL + AWS S3 + Vercel
- **성장 단계**: Redis 캐싱 추가, CDN 적용
- **확장 단계**: 마이크로서비스, Kubernetes, 글로벌 확장
