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
### API 
#### Health Check
- server health check API
- Request
    - method: GET
    - URI: /health
- Response
    - Status: 200 OK
#### initialize data
- 저장된 csv파일을 읽어들여서 DB에 저장하는 API
- Request
    - method: GET
    - URI: /init
- Response
    - Status: 200 OK
#### Program 조회 API
- 저장된 생태 관광 프로그램 조회
- Request
    - method: GET
    - URI
        - /program/{progId}
        - {progId}: 조회하고자 하는 Program ID
    - Header
        - X-AUTH-TOKEN : {access token}
        - {access token}: 사용자 인증을 통해 획득한 access token
- Response
    - Status: 200 OK
    - Body:
        - id: program ID
        - name: program 이름
        - theme: program 테마
        - region: 서비스 지역
        - introduction: 프로그램 소개
        - description: 프로그램 상세 소개
>```        
>{
>    "id": 1,
>    "name": "자연과 문화를 함께 즐기는 설악산 기행",
>    "theme": "문화생태체험,자연생태체험,",
>    "region": "강원도 속초",
>    "introduction": "설악산 탐방안내소, 신흥사, 권금성, 비룡폭포",
>    "description": "설악산은 왜 설악산이고, 신흥사는 왜 신흥사일까요? 설악산에 대해 정확히 알고, 배우고, 느낄 수 있는 당일형 생태관광입니다."
>}
>```
#### Program 추가 API
- 신규 프로그램 추가
- Request
    - method: POST
    - URI
        - /program
    - Header
        - X-AUTH-TOKEN : {access token}
        - {access token}: 사용자 인증을 통해 획득한 access token
        - Content-Type : application/json
    - Body
        - name: program 이름
        - theme: program 테마
        - region: 서비스 지역
        - introduction: 프로그램 소개
        - description: 프로그램 상세 소개
- Response
    - Status: 200 OK
#### Program 수정 API
- 기존 프로그램 수정
- Request
    - method: PUT
    - URI
        - /program/{progId}
        - {progId}: 변경하고자 하는 program ID
    - Header
        - X-AUTH-TOKEN : {access token}
        - {access token}: 사용자 인증을 통해 획득한 access token
        - Content-Type : application/json
    - Body
        - name: program 이름
        - theme: program 테마
        - region: 서비스 지역
        - introduction: 프로그램 소개
        - description: 프로그램 상세 소개
- Response
    - Status: 200 OK
#### Program 삭제 API
- 기존 프로그램 삭제
- Request
    - method: DELETE
    - URI
        - /program/{progId}
        - {progId}: 변경하고자 하는 program ID
    - Header
        - X-AUTH-TOKEN : {access token}
        - {access token}: 사용자 인증을 통해 획득한 access token
- Response
    - Status: 200 OK
#### 특정 지역 Program 검색 API
- 특정 지역 Program 검색
- Request
    - method: GET
    - URI
        - /program/region/name
    - Header
        - X-AUTH-TOKEN : {access token}
        - {access token}: 사용자 인증을 통해 획득한 access token
        - Content-Type : application/json
    - Body
        - region: 서비스 지역
- Response
    - Status: 200 OK
    - Body
        - List
            - region: 지역 code
            - programs
                - List
                    - prgm_name: 프로그램 이름
                    - theme: 프로그램 테마
>```
>[
>    {
>        "region": 36,
>        "programs": [
>            {
>                "prgm_name": "(1박2일)자연으로 떠나는 행복여행",
>                "theme": "문화생태체험,자연생태체험,"
>            },
>            {
>                "prgm_name": "오대산국립공원 힐링캠프",
>                "theme": "숲 치유,"
>            },
>            {
>                "prgm_name": "소금강 지역문화 체험",
>                "theme": "자연생태,"
>            }
>        ]
>    },
>    {
>        "region": 59,
>        "programs": [
>            {
>                "prgm_name": "오감만족! 오대산 에코 어드벤처 투어",
>                "theme": "아동·청소년 체험학습"
>            }
>        ]
>    }
>]
>```
#### 프로그램 소개에 Keyword 포함하는 서비스 지역 개수 출력 API
- Request
    - method: GET
    - URI
        - /count/program/from_intro
    - Header
        - X-AUTH-TOKEN : {access token}
        - {access token}: 사용자 인증을 통해 획득한 access token
        - Content-Type : application/json
    - Body
        - keyword: 입력 keyword
- Response
    - Status: 200 OK
    - Body
        - keyword: 입력 keyword
        - programs
                - List
                    - region: 지역이름
                    - count: 등장 지역 갯수
>```
>{
>    "keyword": "세계문화유산",
>    "programs": [
>        {
>            "region": "경상북도 경주시",
>            "count": 2
>        }
>    ]
>}
>```
#### 프로그램 상세 정보에 Keyword 출현 빈도 계산 API
- Request
    - method: GET
    - URI
        - /count/keyword/from_desc
    - Header
        - X-AUTH-TOKEN : {access token}
        - {access token}: 사용자 인증을 통해 획득한 access token
        - Content-Type : application/json
    - Body
        - keyword: 입력 keyword
- Response
    - Status: 200 OK
    - Body
        - keyword: 입력 keyword
        - count: 등장 횟수
>```
>{
>    "keyword": "자연",
>    "count": 79
>}
>```

#### 지역별 추천 프로그램 출력 API
- Request
    - method: GET
    - URI
        - /program/recommendation
    - Header
        - X-AUTH-TOKEN : {access token}
        - {access token}: 사용자 인증을 통해 획득한 access token
        - Content-Type : application/json
    - Body
        - region: 지역 이름
        - keyword: 입력 keyword
>```
>{
>   "region":"강원",
>   "keyword":"여행"
>}
>```
- Response
    - Status: 200 OK
    - Body
        - program: Program code
>```
>{
>    "program": "e83fe5862ca8da42fcfae1aa33c75e40"
>}
>```
### Project package 구조
```
.
├── java
│   └── com
│       └── kakaopay
│           └── ecotour
│               ├── config                      // 각종 Config 객체
│               ├── controller                  // API Controller
│               ├── dao
│               │   ├── entity                  // JPA Entity
│               │   └── repository              // JPA Repository
│               ├── exception                   // Custom Exception 정의
│               │   └── handle                  // Exception handler
│               ├── filter                      // 사용자 Auth를 위한 filter
│               ├── manager                     // Manager interface 위치
│               │   └── impl                    // Manager 구현
│               ├── model
│               │   ├── auth                    // 인증관련 DTO
│               │   ├── http                    // http response, request body 객체
│               │   └── recomm                  // 추천 관련 DTO
│               ├── provider                    // 인증 Token Provider
│               ├── service                     // API 서비스 interface
│               │   └── impl                    // API 서비스 구현
│               └── util                        // 간편 기능 제공용 util
└── resources                                   // properties 파일, csv파일 등 resource
```
### Project Block Diagram
## 문제해결 전략
### ORM Entity 구조
### 추천 알고리즘 개발
#### 최초 아이디어
##### 알고리즘
1. 추천의 Source가 되는 theme, introduction, description column에서 입력 keyword count
2. Column별로 weight를 곱
3. 각 column값별 weighted count를 합
##### 문제점
- 모든 문서에 빈출 단어는 특정 문서의 특징을 반영 불가
- 문서의 특징보다 단어의 수, 문서의 길이에 의해 영향을 받을 가능성 존재
##### 해결방법
- TF-IDF 도입 방법 고려
#### TF-IDF 활용한 추천 알고리즘
##### 알고리즘
- 특정 record의 특정 column의 내용을 문서(document)로 정의
- 문서에서 추출된 명사를 Term으로 정의
- 전체 record의 특정 column을 문서군으로 정의
1. 추천의 Source가 되는 theme, introduction, description column에 대해서 각각 아래 과정을 수행
    1. 문서군에서 형태소 분석기를 통해 명사(Term)를 추출
    2. 각 Term이 전체 문서대비 몇개의 문서에 존재하는지 계산 (Document Frequency, DF)
    3. 각 Term별로 각 문서에 몇번 있는지 확인 (Term Frequency. TF)
    4. 문서별 개별 Term의 TF-IDF 값 계산
2. column 별 입력받은 Keyword에 해당하는 TF-IDF 값 추출
3. 2번의 값에 column별 weight 곱
4. 3번으로 나온 3개의 weighted TF-IDF를 합
5. 입력받은 keyword에 해당하는 Weighted TF-IDF 추출
6. 5번의 Weighted TF-IDF를 내림차순 정렬
7. 6번의 목록에서 첫번째 문서 추천
##### 문제점
- 입력받은 keyword가 복합어(ex: 생태체험) 일 경우, 추천의 Source data는 Term별로 분해(생태 + 체험)가 되어서 추천하지 못하는 문제 발생
##### 해결방법
- 입력받은 keyword도 동일한 형태소 분석기로 Term을 추출하여, 개별 Term에 대해서 추천 알고리즘 활용하고 이를 총합하여 추천
#### TF-IDF 활용한 추천 알고리즘
##### 알고리즘
1. 위의 TF-IDF를 이용한 추천 알고리즘 4번 까지 수행
2. 입력받은 keyword를 형태소 분석
3. 분석된 Termed keyword에 해당하는 Weighted TF-IDF 추출
4. 3번으로 나온 3개의 Weighted TF-IDF를 합
5. 개별 Termed keyword에 대한 4번 값을 총합
6. 5번의 목록을 내림차순 정렬
7. 6번의 목록에서 첫번째 문서 추천
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
