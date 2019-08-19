# eco-program-service
## 과제 개요
- 주어진 지역기반의 생태 관광 정보를 활용할 수 있는 API서버를 개발
### 요구사항 분석
#### 필수 요구사항
- [x] 데이터 파일을 DB에 저장 API
- [x] 데이터 조회/추가/수정 API
- [x] 지역 이름을 입력받고, 해당 지역에서 진행되는 지역코드, 프로그램, 테마 조회 API
- [x] 프로그램 소개(introduction) 컬럼에서 keyword를 포함하는 서비스 지역 개수 count API
- [x] 프로그램 상세정보(description) 컬럼에서 입력 keyword의 출현빈도 count API
#### Optional 요구사항
- [x] 지역과 keyword를 입력받아서 가장 적합한 프로그램 추천 API
- [x] JWT(Json Web Token)을 이용한 인증 체계
    - [x] signup: 계정생성
    - [x] signin: 로그인
    - [x] refresh: Token 갱신
#### 개인적 Objective
- [x] TF-IDF 방식의 추천 알고리즘 개발
- [x] Top-K개의 프로그램 추천할 수 있도록 확장
- [x] Access Token과 Refresh Token을 이용한 Token 인증 체계 개발
- [x] Java의 함수형 언어적 특징 적극 활용(Lambda, Optional, forEach 등)
- [ ] 개인의 추천 keyword 질의 내역을 이용한 개인화 추천 알고리즘
- [ ] cache를 활용한 API 지연시간 감소
## 빌드 및 실행 방법
### Ubuntu
```
$ chmod +x gradlew
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
- swagger
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
>```
>{
>  "region": "평창군"
>}
>```
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


#### 지역 code로 Program 검색 API
- Request
    - method: GET
    - URI
        - /program/region/code/{regionCode}
        - {regionCode}: 지역 code
    - Header
        - X-AUTH-TOKEN : {access token}
        - {access token}: 사용자 인증을 통해 획득한 access token
- Response
    - Status: 200 OK
    - Body
        - List
            - id: Program ID
            - name: Program 이름
            - theme: Program 테마
            - introduction: 프로그램 소개
            - description: 프로그램 상세 소개
>```
>[
>    {
>        "id": 104,
>        "name": "복잡한 일상을 떠나 자연과 하나되는 기운충전 여행(1박2일)",
>        "theme": "아동·청소년 체험학습,",
>        "region": "충청북도 보은군 속리산면 상판리 법주사로 84",
>        "introduction": "수려한 속리산국립공원 탐방과 함께 흥미로운 체험활동이 어우러진 프로그램",
>        "description": "오리숲 탐방 및 법주사 문화해설, 야행성 생물관찰, 천연염색 체험, 기마체험, 너나들이, 서바이벌 게임 등"
>    },
>    {
>        "id": 105,
>        "name": "햇빛 한 모금 바람 한 줄기도 소중한 속리산 휴식여행(2박3일형)",
>        "theme": "아동·청소년 체험학습,",
>        "region": "충청북도 보은군 속리산면 상판리 법주사로 84",
>        "introduction": "청주지역의 다양한 문화재들 및 속리산국립공원 탐방과 함께 다양한 체험이 어우러진 프로그램",
>        "description": "너나들이(2회), 환경빙고, 도미노 게임\r 캠프파이어, 레크레이션"
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
>```
>{
>  "keyword": "자연"
>}
>```
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

#### Top K개 지역별 추천 프로그램 출력 API
- Request
    - method: GET
    - URI
        - /program/recommendation/topk/{topk}
        - {topk}: 추천 갯수
    - Header
        - X-AUTH-TOKEN : {access token}
        - {access token} : 사용자 인증을 통해 획득한 access token
        - Content-Type : application/json
    - Body
        - region: 지역 이름
        - keyword: 입력 keyword
>```
>{
>   "region":"태안",
>   "keyword":"자연"
>}
>```
- Response
    - Status: 200 OK
    - Body
        - program: Program code
>```
>[
>    {
>        "program": "a9ce17b86efc2fdce015505646a0bc8f"
>    },
>    {
>        "program": "0dbcd284fb7e0c70dc28be35e211d8bd"
>    }
>]
>```

#### 회원 가입 API
- Request
    - method: GET
    - URI
        - /auth/signup?id={id}&password={password}
        - {id}: 사용자 id
        - {password}: 사용자 password
- Response
    - 가입 완료
        - Status: 200 OK
    - 가입 거절
        - Status: 403 Forbidden

#### Token 발급 API
- Request
    - method: GET
    - URI
        - /auth/signin?id={id}&password={password}
        - {id}: 사용자 id
        - {password}: 사용자 password
- Response
    - 발급 완료
        - Status: 200 OK
        - Body
            - access_token: API 사용시 Header에 추가하여 인증하기 위한 token, 유효기간을 상대적으로 짧게 설정
            - refresh_token: Token 재발행을 목적으로 하는 token, 유효기간을 상대적으로 길게 설정
    - 발급 거절
        - Status: 403 Forbidden

#### Token refresh API
- Request
    - method: GET
    - URI
        - /auth/refresh?id={id}&password={password}
        - {id}: 사용자 id
        - {password}: 사용자 password
    - Header
        - Authorization : Bearer {refresh token}
        - {refresh token}: 발급된 refresh token
- Response
    - 발급 완료
        - Status: 200 OK
        - Body
            - access token: 재발급된 access token
>```
>eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1MSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE1NjYxNDA5MjQsImV4cCI6MTU2NjE0NDUyNH0.-bLzO_2MrkJ-Igb3nzRGyWC0GeSlUzF6OqjvO_rCJzI
>```
    - 발급 거절
        - Status: 403 Forbidden

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
![Alt text](/md_img/blockdiagram.jpg)
## 문제해결 전략
### JPA통한 ORM 구성
#### Program과 Region 정보 ER Diagram
- Program과 Region정보(지역이름, 지역code)를 한 table로 구성할 경우 1차 정규화 대상
	- 따라서 지역 정보의 중복을 최소화 하기 위해서 table분리가 필요
- Business Logic상 Program은 특정 지역에 속해 있고, 특정 지역은 여러개의 Program을 개최할 수 있다.
	- 따라서 Program기준으로 @ManyToOne 관계
- 추천 API에서 Program code를 출력하는 것이 요구사항이므로 Program table에 prog_code column 추가
	- unique가 보장되어야 하는 code이므로 MD5 Hash를 이용하여 생성
		- MD5 seed : Program 이름 + Program 테마 + Program 지역이름 + Program 설명 + Program 상세 설명
- Region의 code 값을 Region table의 PK로 설정
	- 요구사항에 region_code에 대한 특별한 형식 제약이 없었고
	- ECO_TOUR_PROGRAM table과 join에 활용되므로 string보다 숫자를 활용
![Alt text](/md_img/program_erd.jpg)

``` java
@Entity
public class EcoTourProgram {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private Long id;
	@Column(name="name", nullable = false)
	private String name;
	@Column(name="theme", nullable = false)
	private String theme;
	
	@ManyToOne(targetEntity = Region.class, fetch = FetchType.EAGER)
	@JoinColumn(name="code")
	private Region region;

	@Column(name="introduction")
	private String introduction;
	@Column(name="description", length = 500)
	private String description;
	
	@Column(name="prog_code", nullable = false)
	private String progCode;
	@Column(name="created_time")
	@CreationTimestamp
	private Timestamp createdTime;
	@Column(name="modified_time")
	@UpdateTimestamp
	private Timestamp modifiedTime;
	
            '''
            
}
```
``` java
@Entity
public class Region {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "code")
	private Long code;
	@Column(name = "region_name")
	private String regionName;
	
            '''
}
```
#### 사용자 정보 ER Diagram
- 사용자 signup 정보 저장 및 Role 저장
- Password 는 encoding 된 값으로 저장
![Alt text](/md_img/user_erd.jpg)

``` java
@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 2008055523276541691L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idx;

	@Column(nullable = false, unique = true)
	private String userId;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = false)
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<String> ();
            '''	
}
```
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
	- column별 weight는 application.properties 파일에 저장(0.0 이상, 1.0 이하의 값)
	```
	# recommendation weight (0.0 ~ 1.0)
	recomm.weight.theme=0.9
	recomm.weight.intro=0.85
	recomm.weight.desc=0.8
	```
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
- JWT (Json Web Token): token을 통해 사용자 인증을 하기 위한 기술
#### Access Token
- 사용자 요청시 특정 expire time을 가지는 token을 return
- 사용자는 해당 token을 API 호출시 X-AUTH-TOKEN header에 추가하여 호출
- Filter에서 X-AUTH-TOKEN 을 검사하여 사용자 인증 여부 판단
	- 인증 실패시 403 Forbidden 응답
	- 내부적으로는 SignInFailedException이라는 Custom Exception을 정의하여 throw
#### Refresh Token
- 사용자의 Access Token이 유효기간이 지났을 경우 사용자는 Token을 refresh 시도
- 이때 이미 Access Token이 유효하지 않은 상황이므로, 최초 signin 시 두가지 token을 발행
	- Access Token: API 사용시 인증에 활용, 유효기간이 짧음
	- Refresh Token: Access Token을 refresh하기 위해 사용, 유효기간이 상대적으로 길다.
- Access Token이 만료되면 사용자는 refresh API를 통해 Refresh Token을 header에 담아 호출
	- Header 정보
		- Authorization : Bearer {refresh token}
	- 인증 실패시 403 Forbidden 응답
- 새롭게 발급받은 token으로 사용자는 다시 API 사용
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
