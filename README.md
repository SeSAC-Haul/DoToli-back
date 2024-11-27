
# 🌰 협업하는 도토리

프로젝트 기간: 2024.10.03 ~ 2024.10.29

개인과 팀을 위한 할 일 관리 서비스
  1. 개인 및 팀 단위로 할 일(Task)을 관리할 수 있는 할 일 관리 시스템입니다.
  2. 실시간 협업과 진행 상황 공유로 팀의 생산성을 높이고, 개인의 성장을 트래킹할 수 있는 태스크 플래닝 도구입니다.

### 📚 [Haul Notion 보러가기](https://www.notion.so/11038cc0d0e9801e9702e1b4fa1393f9?pvs=4)
<br />

# :globe_with_meridians:인프라 아키텍처
<p align="center">
  <img src="https://github.com/user-attachments/assets/2aba6dc9-1a6d-4abf-b5b5-8190a610971a" alt="architecture">
</p>
<br />

## 🏗️ 주요 인프라 특징

### 1. **고가용성 설계**
   * 2개의 가용영역(AZ)을 활용한 멀티 AZ 구성
   * 애플리케이션 서버의 Auto Scaling 구성
   * 이중화된 NAT Gateway로 장애 대응

### 2. **네트워크 아키텍처**
   * 계층화된 서브넷으로 구성된 VPC
   * 인터넷 연결용 퍼블릭 서브넷
   * EC2 인스턴스를 위한 프라이빗 앱 서브넷
   * RDS를 위한 격리된 DB 서브넷
   * 보안적인 외부 통신을 위한 NAT Gateway

### 3. **부하 분산 및 성능 최적화**
   * Route 53을 통한 DNS 관리
   * Elastic Load Balancing을 통한 트래픽 분산
   * CloudFront CDN을 활용한 컨텐츠 전송 최적화
   * S3를 통한 정적 리소스 관리 및 백업

### 4. **데이터베이스 구성**
   * 프라이빗 서브넷에 위치한 Amazon RDS
   * 높은 보안성을 위한 격리된 DB 서브넷 구성

<br />

# 🗃️ ERD
<p align="center"> 
<img src="https://github.com/user-attachments/assets/3b4e9810-4a33-4a06-9ac5-b57e58a0951e" alt="인프라 아이콘">
</p>
<br />

# 🛠️ 기술스택
### [Backend]<br />
<p>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white">
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white">
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/JPA-59666C?style=for-the-badge&logo=hibernate&logoColor=white">
    <img src="https://img.shields.io/badge/QueryDSL-4479A1?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAxMDAgMTAwIj48ZyBmaWxsPSIjZmZmIj48Y2lyY2xlIGN4PSI3MCIgY3k9IjUwIiByPSIxMiIvPjxjaXJjbGUgY3g9IjIwIiBjeT0iNTAiIHI9IjgiLz48Y2lyY2xlIGN4PSIyNSIgY3k9IjM1IiByPSI3Ii8+PGNpcmNsZSBjeD0iMzUiIGN5PSIyNSIgcj0iNiIvPjxjaXJjbGUgY3g9IjUwIiBjeT0iMjAiIHI9IjUiLz48Y2lyY2xlIGN4PSI2NSIgY3k9IjI1IiByPSI0Ii8+PGNpcmNsZSBjeD0iNzUiIGN5PSIzNSIgcj0iMyIvPjxjaXJjbGUgY3g9Ijc1IiBjeT0iNjUiIHI9IjIuNSIvPjxjaXJjbGUgY3g9IjY1IiBjeT0iNzUiIHI9IjIiLz48Y2lyY2xlIGN4PSI1MCIgY3k9IjgwIiByPSIxLjUiLz48Y2lyY2xlIGN4PSIzNSIgY3k9Ijc1IiByPSIxIi8+PGNpcmNsZSBjeD0iMjUiIGN5PSI2NSIgcj0iMC43NSIvPjwvZz48L3N2Zz4=&logoColor=white">
  <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white">
  <img src="https://img.shields.io/badge/JWT-D63AFF?style=for-the-badge&logo=json-web-tokens&logoColor=white">
  <img src="https://img.shields.io/badge/WebSocket-4479A1?style=for-the-badge&logo=socket.io&logoColor=white">
  
</p>

### [Infrastructure]<br />
<p>
<img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZmlsbD0iI2ZmZiIgZD0iTTIgMTRDOCAxOCAxNiAxOCAyMiAxNEwyMCAxNkMyMCAxNiA4IDIwIDQgMTZaIi8+PC9zdmc+">
  <img src="https://img.shields.io/badge/VPC-FF9900?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA2NCA2NCI+PHBhdGggZmlsbD0iI2ZmZiIgZD0iTTU5LjcgMjEuN0wzMy4zIDguM2MtLjgtLjQtMS44LS40LTIuNiAwTDQuMyAyMS43Yy0uOC40LTEuMyAxLjItMS4zIDIuMXYxNi40YzAgLjkuNSAxLjcgMS4zIDIuMWwyNi40IDEzLjRjLjQuMi45LjMgMS4zLjNzLjktLjEgMS4zLS4zbDI2LjQtMTMuNGMuOC0uNCAxLjMtMS4yIDEuMy0yLjFWMjMuOGMwLS45LS41LTEuNy0xLjMtMi4xek0zMiAxMS43bDIyLjQgMTEuNEwzMiAzNC41IDkuNiAyMy4xIDMyIDExLjd6TTcgMjUuMmwyMyAxMS43djIyLjlMNyAzNy4xVjI1LjJ6bTI3IDAuMXYyMi45bDIzLTExLjdWMjUuMkwzNCAyNS4zeiIvPjwvc3ZnPg==">

  <img src="https://img.shields.io/badge/Route%2053-8C4FFF?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA2NCA2NCI+PHBhdGggZmlsbD0iI2ZmZiIgZD0iTTMyIDJDMTUuNCAxLjMgMS43IDE0LjQgMS43IDMxYzAgMTYuNiAxMy40IDMwIDMwIDMwczMwLTEzLjQgMzAtMzBDNjEuNyAxNC40IDQ4LjYgMS4zIDMyIDJ6bTAgNTRjLTEzLjggMC0yNS0xMS4yLTI1LTI1czExLjItMjUgMjUtMjUgMjUgMTEuMiAyNSAyNS0xMS4yIDI1LTI1IDI1em0wLTM5YzcuNyAwIDE0IDYuMyAxNCAxNHMtNi4zIDE0LTE0IDE0LTE0LTYuMy0xNC0xNCA2LjMtMTQgMTQtMTR6Ii8+PC9zdmc+">
  <img src="https://img.shields.io/badge/CloudFront-F38020?style=for-the-badge&logo=cloudflare&logoColor=white">
  <img src="https://img.shields.io/badge/S3-569A31?style=for-the-badge&logo=amazon-s3&logoColor=white">
  <img src="https://img.shields.io/badge/ELB-FF6200?style=for-the-badge&logo=elastic&logoColor=white">
  <img src="https://img.shields.io/badge/Auto%20Scaling-FF4F8B?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA2NCA2NCI+PHBhdGggZmlsbD0iI2ZmZiIgZD0iTTU3LjcgMjBoLTUxYy0yLjIgMC00IDEuOC00IDR2MjRjMCAyLjIgMS44IDQgNCA0aDUxYzIuMiAwIDQtMS44IDQtNFYyNGMwLTIuMi0xLjgtNC00LTR6bS0yNSAyNGgtMVYyOGgxdjE2em05IDBoLTFWMjhoMXYxNnptLTE4IDBoLTFWMjhoMXYxNnptOS04aC0xdi04aDF2OHoiLz48L3N2Zz4=">
  <img src="https://img.shields.io/badge/EC2-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=white">
  <img src="https://img.shields.io/badge/Multi%20AZ-FF9900?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My
  <img src="https://img.shields.io/badge/RDS-527FFF?style=for-the-badge&logo=amazon-rds&logoColor=white">
</p><br/>


# 💡 핵심 기술적 차별점

### 1. 체계적인 예외 처리

```
📂 error
 ├── ErrorCode            # 표준화된 에러 코드 (enum)
 ├── ErrorResponse        # 일관된 에러 응답 포맷
 └── 📂 exception
      ├── BusinessBaseException     # 비즈니스 예외 기본 클래스
      └── DomainException          # 도메인별 커스텀 예외
```
* 계층별 세분화된 예외 처리
* 표준화된 에러 코드와 응답 포맷
* 클라이언트 친화적인 에러 메시지

### 2. 보안 강화
* JWT 기반 인증 이메일 인증 시스템
* AWS 보안 그룹을 통한 네트워크 보안
* HTTPS 적용을 통한 데이터 암호화
<br/>

# 💥 트러블 슈팅
### 1. Task 필터링의 날짜/시간 처리 문제 해결

#### 문제 정의
Task 엔티티의 deadline 필드(LocalDateTime)와 프론트엔드의 날짜 문자열("2024-03-15") 형식 불일치로 인한 필터링 오작동 문제가 발생했습니다. 예를 들어 "2024-03-15 14:30:00"과 같이 저장된 데이터를 "2024-03-15"로 검색할 때 정확한 필터링이 불가능했습니다.

#### 해결 방안 모색 및 적용
QueryDSL을 활용하여 날짜와 시간을 정교하게 처리하는 로직을 구현했습니다. 프론트엔드에서 받은 날짜 문자열을 해당 일자의 시작 시점(00:00:00)으로 변환하여 비교하도록 수정했습니다:

```java
// 날짜 변환 및 필터링 처리
LocalDateTime deadline = null;
if (deadlineStr != null) {
    LocalDate deadlineDate = LocalDate.parse(deadlineStr);
    deadline = deadlineDate.atStartOfDay();
}

if (deadline != null) {
    LocalDateTime standardDeadline = deadline.toLocalDate().atStartOfDay();
    builder.and(task.deadline.loe(standardDeadline));
    .orderBy(
        Expressions.stringTemplate(
            "ABS(TIMESTAMPDIFF(SECOND, {0}, {1}))",
            task.deadline,
            standardDeadline
        ).asc()
    )
}
```

---
_이 문제 해결을 통해 날짜/시간 데이터 처리 시 프론트엔드와 백엔드 간의 데이터 형식 일관성 유지의 중요성과 QueryDSL의 표현식 기능을 활용한 복잡한 날짜/시간 연산 처리 방법을 학습했습니다._

<br/>

### 2. React SPA CloudFront 배포 문제 해결

#### 문제 정의
React SPA 배포 시 CloudFront와 S3 설정은 올바르게 했으나, 애플리케이션의 특정 경로 접근 시 404(Not Found)와 403(Forbidden) 에러가 발생하여 페이지가 정상적으로 로드되지 않는 문제가 있었습니다.

#### 해결 방안 모색 및 적용
SPA의 클라이언트 사이드 라우팅 특성을 고려하여, CloudFront의 Error Pages 설정에서 404와 403 에러 발생 시 index.html로 리다이렉션하도록 구성했습니다. 구체적으로는 각 에러 코드에 대해 아래와 같이 설정하여 모든 라우트에서 애플리케이션이 정상적으로 로드되도록 문제를 해결했습니다.

```json
{
   "ErrorCode": "404",
   "ResponsePagePath": "/index.html",
   "ResponseCode": "200"
},
{
   "ErrorCode": "403",
   "ResponsePagePath": "/index.html",
   "ResponseCode": "200"
}
```

---
_이 문제 해결을 통해 CloudFront를 통한 SPA 배포 시 Error Pages 설정의 중요성을 이해할 수 있었습니다._

# 🚀 팀원 소개

| 이름 | 역할 | 기여 내용 | GitHub |
|:---:|:---:|:---|:---:|
| 오찬근 | 팀장 | • 프로젝트 총괄<br>• 기술 스택 설계 | https://github.com/Chan-GN |
| 임진희 | 기술 개발 |  | https://github.com/liimjiin |
| 임혜린 | 기술 개발 |  | https://github.com/hyerin315 |
| 박종호 | 기술 개발 |  | https://github.com/cuteJJong |
