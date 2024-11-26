# 🏰 SeSAC-Haul

### 🌰 협업하는 도토리노트

프로젝트 기간: 2024.09.30 ~ 2024.10.29

개인과 팀을 위한 할 일 관리 서비스
  1. 개인 및 팀 단위로 할 일(Task)을 관리할 수 있는 TODO 시스템입니다.
  2. 실시간 협업과 진행 상황 공유로 팀의 생산성을 높이고, 개인의 성장을 트래킹할 수 있는 태스크 플래닝 도구입니다.

### 📚 [Haul Notion 보러가기](https://www.notion.so/11038cc0d0e9801e9702e1b4fa1393f9?pvs=4)
<br />
<br />

# 📏 Architecture
<p align="center">
  <img src="https://github.com/user-attachments/assets/a3548a7f-47ba-4694-bac6-17eb8b059d07" alt="architecture">
</p>

<details>
<summary><h3>🏗️ 주요 인프라 특징</summary>

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

</details>

# 🗃️ ERD
<details>
<summary><h3>ERD 보기</summary>

</details>

# 🛠️ 기술스택
Frontend<br />
<p>
  <img src="https://img.shields.io/badge/React_18-61DAFB?style=for-the-badge&logo=react&logoColor=black">
  <img src="https://img.shields.io/badge/TailwindCSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white">
  <img src="https://img.shields.io/badge/WebSocket-010101?style=for-the-badge&logo=socket.io&logoColor=white">
  <img src="https://img.shields.io/badge/Custom_Hooks-61DAFB?style=for-the-badge&logo=react&logoColor=black">
</p>
Backend<br />
<p>
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white">
  <img src="https://img.shields.io/badge/JPA-59666C?style=for-the-badge&logo=hibernate&logoColor=white">
  <img src="https://img.shields.io/badge/QueryDSL-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/WebSocket_STOMP-010101?style=for-the-badge&logo=socket.io&logoColor=white">
  <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white">
</p>
Infrastructure<br />
<p>
  <img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white">
  <img src="https://img.shields.io/badge/VPC-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white">
  <img src="https://img.shields.io/badge/EC2-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=white">
  <img src="https://img.shields.io/badge/RDS-527FFF?style=for-the-badge&logo=amazon-rds&logoColor=white">
  <img src="https://img.shields.io/badge/S3-569A31?style=for-the-badge&logo=amazon-s3&logoColor=white">
  <img src="https://img.shields.io/badge/CloudFront-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white">
  <img src="https://img.shields.io/badge/Route_53-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white">
  <img src="https://img.shields.io/badge/Multi_AZ-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white">
  <img src="https://img.shields.io/badge/Auto_Scaling-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white">
  <img src="https://img.shields.io/badge/ELB-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white">
</p>

# 🚀 팀원 소개

| 이름 | 역할 | 기여 내용 | GitHub |
|:---:|:---:|:---|:---:|
| 오찬근 | 팀장 | • 프로젝트 총괄<br>• 기술 스택 설계<br>• CI/CD 파이프라인 구축 | https://github.com/Chan-GN |
| 임진희 | 기술 개발 |  | https://github.com/liimjiin |
| 임혜린 | 기술 개발 |  | https://github.com/hyerin315 |
| 박종호 | 기술 개발 |  | https://github.com/cuteJJong |

