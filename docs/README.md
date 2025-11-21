# LetsLearn 프로젝트 문서

온라인 강의 플랫폼 프로젝트의 전체 계획 및 설계 문서입니다.

## 문서 구조

### 📋 01. Planning (기획)
프로젝트의 전체적인 방향과 계획을 담은 문서들입니다.

- **[01. 프로젝트 개요](./01-planning/01-project-overview.md)** - 프로젝트의 목표, 타겟 사용자, 비즈니스 모델
- **[02. 개발 로드맵](./01-planning/02-roadmap.md)** - 단계별 개발 계획 및 일정

### 📝 02. Requirements (요구사항)
시스템에서 구현해야 할 기능들을 정리한 문서입니다.

- **[01. 기능 요구사항](./02-requirements/01-functional-requirements.md)** - 사용자 관리, 강의 관리, 수강 관리 등 모든 기능 명세

### 🏗️ 03. Architecture (아키텍처)
시스템의 기술적인 구조와 설계를 담은 문서들입니다.

- **[01. 기술 스택](./03-architecture/01-tech-stack.md)** - 프론트엔드, 백엔드, 인프라 기술 선택
- **[02. 시스템 아키텍처](./03-architecture/02-system-architecture.md)** - 전체 시스템 구조 및 컴포넌트
- **[03. 데이터베이스 스키마](./03-architecture/03-database-schema.md)** - ERD 및 테이블 설계
- **[04. MySQL 설정 가이드](./03-architecture/04-mysql-setup-guide.md)** - MySQL 설치 및 연결 설정

### 🎨 04. Design (디자인)
UI/UX 디자인 가이드라인 문서입니다.

- **[01. UI/UX 디자인](./04-design/01-ui-ux-design.md)** - 디자인 시스템, 컬러, 타이포그래피, 레이아웃

## 빠른 시작

### 1. 프로젝트 이해하기
1. [프로젝트 개요](./01-planning/01-project-overview.md)를 읽고 전체적인 방향을 파악하세요.
2. [기능 요구사항](./02-requirements/01-functional-requirements.md)을 확인하여 구현할 기능들을 파악하세요.

### 2. 기술 스택 검토
[기술 스택](./03-architecture/01-tech-stack.md) 문서를 통해 사용할 기술들을 확인하세요.

### 3. 개발 시작
[개발 로드맵](./01-planning/02-roadmap.md)을 참고하여 단계별로 개발을 진행하세요.

## 주요 특징

- 📚 **온라인 강의 플랫폼**: 강사와 학생을 연결하는 교육 플랫폼
- 🎥 **동영상 스트리밍**: HLS 기반 적응형 비트레이트 스트리밍
- 💳 **결제 시스템**: 토스페이먼츠/Stripe 통합
- 📊 **학습 관리**: 진도율 추적, 수료증 발급
- 💬 **커뮤니티**: Q&A, 수강평, 댓글 시스템

## 기술 스택 요약

- **Frontend**: Next.js 14, TypeScript, Tailwind CSS
- **Backend**: Node.js, Express/NestJS
- **Database**: PostgreSQL, Redis
- **Storage**: AWS S3
- **Deployment**: Vercel, AWS

## 개발 단계

| Phase | 기간 | 목표 |
|-------|------|------|
| Phase 1 | 1-2개월 | MVP 출시 |
| Phase 2 | 3-4개월 | 핵심 기능 확장 |
| Phase 3 | 5-6개월 | 고급 기능 추가 |
| Phase 4 | 7개월+ | 확장 및 운영 |

## 참고 자료

- [인프런](https://www.inflearn.com/) - 참고 플랫폼
- [Udemy](https://www.udemy.com/) - 글로벌 참고 사례
- [Class101](https://class101.net/) - 국내 참고 사례

## 라이센스

MIT License

## 기여하기

이 프로젝트는 현재 개발 중입니다. 기여를 원하시면 이슈를 생성하거나 PR을 보내주세요.

---

**Last Updated**: 2025-11-20
