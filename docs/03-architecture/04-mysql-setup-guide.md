# MySQL 데이터베이스 설정 가이드

## 문제 해결 완료

Spring Boot 애플리케이션의 MySQL 연결 설정을 수정했습니다.

### 수정 내용

**파일**: `src/main/resources/application.yaml`

```yaml
url: jdbc:mysql://localhost:3307/learn_db?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8&allowPublicKeyRetrieval=true
```

`allowPublicKeyRetrieval=true` 파라미터를 추가하여 "Public Key Retrieval is not allowed" 오류를 해결했습니다.

---

## MySQL 설치 및 설정

현재 시스템에 MySQL이 설치되어 있지 않습니다. 다음 방법 중 하나를 선택하여 MySQL을 설치하세요.

### 방법 1: Docker를 사용한 MySQL 설치 (권장)

1. **Docker 설치** (아직 설치되지 않은 경우)
   - Windows/Mac: Docker Desktop 설치
   - Linux: Docker Engine 설치

2. **MySQL 컨테이너 실행**

```bash
docker run --name mysql-letslearn -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=learn_db -p 3307:3306 -d mysql:8.0
```

3. **컨테이너 상태 확인**

```bash
docker ps
```

4. **데이터베이스 접속 테스트**

```bash
docker exec -it mysql-letslearn mysql -uroot -proot
```

### 방법 2: 직접 MySQL 설치

#### Ubuntu/Debian

```bash
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql
sudo systemctl enable mysql
```

#### macOS (Homebrew)

```bash
brew install mysql
brew services start mysql
```

#### Windows

1. [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) 다운로드
2. 설치 프로그램 실행
3. root 비밀번호를 `root`로 설정

### 방법 3: 데이터베이스 생성 (MySQL 설치 후)

MySQL이 실행 중이면 데이터베이스를 생성해야 합니다.

```bash
# MySQL 접속
mysql -u root -p

# 데이터베이스 생성
CREATE DATABASE learn_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 확인
SHOW DATABASES;

# 종료
exit;
```

---

## Spring Boot 애플리케이션 재시작

MySQL이 실행 중이면 Spring Boot 애플리케이션을 다시 시작하세요.

```bash
# Maven
./mvnw spring-boot:run

# Gradle
./gradlew bootRun
```

---

## 추가 설정 옵션

### application.yaml 전체 예시

```yaml
spring:
  application:
    name: learn

  # MySQL Database Configuration
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3307/learn_db?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8&allowPublicKeyRetrieval=true
    username: root
    password: root
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000

  # JPA Configuration
  jpa:
    hibernate:
      ddl-auto: update  # 개발: update, 프로덕션: validate
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQLDialect
        use_sql_comments: true
```

### 환경별 설정 분리 (권장)

**application-dev.yaml** (개발 환경)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/learn_db?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8&allowPublicKeyRetrieval=true
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

**application-prod.yaml** (프로덕션 환경)
```yaml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
```

---

## 트러블슈팅

### 1. 연결 거부 (Connection refused)
- MySQL이 실행 중인지 확인: `sudo systemctl status mysql` (Linux)
- 포트 3307이 열려있는지 확인: `netstat -an | grep 3307`

### 2. 인증 실패 (Access denied)
- 사용자명과 비밀번호 확인
- MySQL에서 사용자 권한 확인:
  ```sql
  GRANT ALL PRIVILEGES ON learn_db.* TO 'root'@'localhost';
  FLUSH PRIVILEGES;
  ```

### 3. 데이터베이스가 존재하지 않음
- 데이터베이스 생성: `CREATE DATABASE learn_db;`

### 4. 타임존 오류
- MySQL 타임존 테이블 초기화:
  ```bash
  mysql_tzinfo_to_sql /usr/share/zoneinfo | mysql -u root -p mysql
  ```

---

## 다음 단계

1. MySQL 설치 및 실행
2. `learn_db` 데이터베이스 생성
3. Spring Boot 애플리케이션 재시작
4. 브라우저에서 `http://localhost:8080` 접속 확인

애플리케이션이 정상적으로 시작되면 Hibernate가 자동으로 테이블을 생성합니다 (`ddl-auto: update` 설정).
