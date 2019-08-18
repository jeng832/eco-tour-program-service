# eco-program-service
## 과제 개요
- 주어진 지역기반의 생태 관광 정보를 활용할 수 있는 API서버를 개발
### 요구사항 분석
#### 필수 요구사항
- 데이터 파일을 DB에 저장 API
- 데이터 조회/추가/수정 API
- 지역 이름을 입력받고, 해당 지역에서 진행되는 지역코드, 프로그램, 테마 조회 API
- 프로그램 소개(introduction) 컬럼에서 keyword를 포함하는 서비스 지역 개수 count API
- 프로그램 상세정보(description) 컬럼에서 입력 keyword의 출현빈도 count API
#### Optional 요구사항
- 지역과 keyword를 입력받아서 가장 적합한 프로그램 추천 API
- JWT(Json Web Token)을 이용한 인증 체계
    - signup: 계정생성
    - signin: 로그인
    - refresh: Token 갱신
#### 개인적 목표
- TF-IDF 방식의 추천 알고리즘 개발
- Top-K개의 프로그램 추천할 수 있도록 확장
- Access Token과 Refresh Token을 이용한 Token 인증 체계 개발
- 실제 개발/운영계 분리 환경을 고려한 개발
- Java의 함수형 언어적 특징 적극 활용(Lambda, Optional, forEach 등)
## 빌드 및 실행 방법
### Ubuntu
```
$ gradlew bootRun
```
### Windows
```
> gradlew.bat bootRun
```
## 개발 프레임워크
### 활용 library
- gradle
- spring-boot
- Komoran 형태소 분석기
- h2database
- apache-commons
### Project package 구조
```
.
├── java
│   └── com
│       └── kakaopay
│           └── ecotour
│               ├── config
│               ├── controller
│               ├── dao
│               │   ├── entity
│               │   └── repository
│               ├── exception
│               │   └── handle
│               ├── filter
│               ├── manager
│               │   └── impl
│               ├── model
│               │   ├── auth
│               │   ├── http
│               │   └── recomm
│               ├── provider
│               ├── service
│               │   └── impl
│               └── util
└── resources
```
### Project Block Diagram
## 문제해결 전략
### ORM Entity 구조
### 추천 알고리즘 개발
- 초기 idea
- 발전
- 문제(복합어 keyword) -> 해결
### JWT 활용
- Access Token / Refresh Token 설명
## 발전 방향
### 개인화 추천 기능 추가
- JWT를 통한 인증 및 로그인 기능과 연동하여, 개인의 추천 keyword 내역을 이용한 개인화 추천 알고리즘
    - 검색기록을 통한 Collaborative Filtering(협업 필터링) 방식
### API 성능 개선
- Redis를 활용한 지연시간 감소
    - GET API 종류에 대해 cache를 활용하여 지연시간 감소
## Lessons & Learned
- JWT 분야에 대한 학습과 구현 경험
- ORM을 활용한 Data 관리
