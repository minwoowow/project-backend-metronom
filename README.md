# 🚇​[Project] 부산시 지하철 실시간 혼잡도 알림 서비스
> [부산대학교 K-Digital Trainig] AI활용 빅데이터분석 풀스택웹서비스 SW 개발자 양성과정  
> 수강중 진행한 프로젝트
<br>

## [시연영상](https://www.youtube.com/watch?v=eH851DO0fqw)

<a href="https://www.youtube.com/watch?v=eH851DO0fqw"><img src="https://github.com/user-attachments/assets/6e4bbf2c-da49-44c6-adde-a4bb4636a216" /></a>

<br>

## 목차
 1. [프로젝트 소개](#1-프로젝트-소개)
 2. [팀구성 및 기술스택](#2-팀구성-및-기술스택)
 3. [구현 기능](#3-구현-기능)
 4. [화면 설계서](#4-화면-설계서)
 5. [REST API 명세](#5-rest-api-명세)
 6. [ERD 명세](#6-erd-명세)
 7. [최단경로 탐색 알고리즘 구현(Dijkstra Algorithm)](#7-최단경로-탐색-알고리즘-구현dijkstra-algorithm)
 8. [프로젝트 회고](#8-프로젝트-회고)
<br>

### 1. 프로젝트 소개
- 프로젝트 이름 : Metronom
- 개발기간 : 2024.06.01 ~ 2024.06.28
- 개발목적 : 지하철 객실의 혼잡도를 실시간으로 알려주는 서비스를 제공함으로써 지하철 이용 승객들의 편의성 향상 및 안전성 증대, 그와 더불어 대중교통의 이용증가라는 선순환 형성
- 2024년 부산광역시 공공/빅데이터 활용 창업경진대회 아이디어 부문 참가, 부산교통공사 공공데이터 활용
- ${\textsf{\color{red}핵심 아이디어 : 지하철 객실내 CCTV로 부터 받아온 이미지 데이터를 컴퓨터 비전을 활용하여 분석, 실시간으로 혼잡도를 알려주는 서비스}}$
<br>

### 2. 팀구성 및 기술스택
  * ### 팀구성
    - #### Back-end
      > **장민우**(본인)  
      > 역할 : 데이터베이스 설계, REST API 구축, 비즈니스 로직 구현
      
      > *기술스택*  
      > <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" />
      > <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" />
      > <img src="https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white" />

    - #### Front-end
      > **김정원**<a href="https://github.com/DevInGarden/K-Digital-MiniProject">(GITHUB Link)</a>  
      > 역할 : 화면 레이아웃 설계, 페이지별 컴포넌트 제작
      
      > *기술스택*  
      > <img src="https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white" />
      > <img src="https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E" />
      > <img src="https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB" />
      > <img src="https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white" /><br>      
<br>

### 3. 구현 기능
 - **출발역 및 도착역 선택** : 사용자 인터페이스를 통해 출발역과 도착역을 한 화면에서 쉽게 선택할 수 있게 구성
 - **실시간 열차 정보** : 선택한 역의 곧 도착예정인 열차의 객실 내 CCTV 이미지 데이터를 전송받아 객실별 혼잡도를 시각화
 - **소요시간 및 경로정보** : 선택된 출발역과 도착역 간의 실시간 소요시간과 경로 정보를 제공(Dijkstra Algorithm 활용)
 - **도착역 주변정보** : 도착역의 출구정보와 주변 주요시설이 담긴 지도 제공(Kakao Map API)
 - **역내 공기질 정보** : 역내 공기질 데이터 제공(부산광역시 실내공기질 실시간 측정자료 API)
 - **통합 화면 설계** : 모든 주요기능을 한 화면에서 직관적으로 볼 수 있도록 통합 화면 레이아웃 구성
<br>

### 4. 화면 설계서
![Main_Page](https://github.com/user-attachments/assets/a73e2d26-ec56-4b49-916b-f67fde998023)

 1. 노선도 화면
    - Client : 출발역과 도착역을 차례로 클릭하여 해당역의 ID 값을 변수에 저장
    - Front-end : 아래 각 컴포넌트 자체적으로 Fetch 진행
  
 2. 도착 정보 화면
    - Front-end : 출발역, 도착역 ID를 담아 API request
    - Back-end : request로 받은 ID를 바탕으로 Dijkstra 알고리즘 사용하여 최단경로 구한 후 DB에 저장되어 있는 도착시간정보를 Response
 
 3. 실시간 혼잡도 정보 화면
    - Font-end : 출발역, 도착역 ID를 담아 API request
    - Back-end : (1) request 받은 ID를 통해 출발역과 상하행 정보를 판단
                 (2) 해당역에 가장 먼저 들어올 열차ID를 통해 해당열차의 객실별 CCTV 이미지 데이터를 종합관제실로 부터 받은 후(미구현)
                 (3) 각 이미지 데이터를 혼잡도예측모델로 분석 후 객실별 혼잡도 값(백분율)을 Response
    - Front-end : Response 받은 혼잡도 값을 구간별로 색깔 지정하여 시각화

 4. 역내 공기질 정보 화면
    - Front-end : 출발역 ID를 API형식에 맞는 ID로 변환후 '실내공기질 실시간 측정자료 API'로 Fetch한 후 response 값을 출력

 5. 도착역 주변정보 화면
    - Front-end : 도착역 ID를 담아 API Request
    - Back-end : request로 받은 ID를 바탕으로 도착역의 위, 경도 데이터를 response
<br>

### 5. REST API 명세
 | Method | URI | Description | 화면 ID |
 | :---: | :---: | :---: | :---: |
 | GET | /getArrivalInfo | 출발역과 도착역간의 최적경로, 환승정보, 소요시간 정보 제공 | *2* |
 | GET | /getCongestionLevel | 해당역에 도착할 열차의 혼잡도 정보 제공 | *3* |
 | GET | /getArrivalTime | 선택한 역에 들어올 열차의 도착시간 정보 제공 | *3* |
 | GET | /getLocation | 선택한 역의 위, 경도 정보 제공 | *5* |
<br>

### 6. ERD 명세
![erd_desc](https://github.com/user-attachments/assets/99f962b8-f25a-40e9-aec4-86f47e94b125)
<br>

### 7. 최단경로 탐색 알고리즘 구현(Dijkstra Algorithm)
![route_map](https://github.com/user-attachments/assets/e6e2f99b-bffe-4366-9ead-5a61aec1c307)

- 호선별로 각 역들을 연결, 각 호선별 환승역 끼리 연결하여 그래프 완성
  - 1호선(40), 2호선(43), 3호선(17), 4호선(14)
  - 괄호안은 node(역) 개수, 각 node를 연결하는 edge는 시간(second) 가중치를 줌
  - 시간 가중치는 부산교통공사 공공데이터에 있는 역간 거리를(km)를 지하철 평균 시속으로 나눠서 초단위로 설정
  - 환승역 이동 간선 포함(ex. 서면(1) -> 서면(2))
  - 환승역 이동시간은 [부산교통공사 홈페이지 내용](https://www.humetro.busan.kr/homepage/cyberstation/map.do) 참조
- 2차원 ArrayList와 Node로 그래프 구현
  ```java
  public class ShortestPath {
	 ...
  private ArrayList<ArrayList<Node>> graph;

  static class Node {	// 다음 노드의 index와 그 노드로 가는데 필요한 cost(가중치)
	 	int dest;  // 다음 노드 index
		int cost;  // 가중치
		...
  ```
  <br>
- PriorityQueue를 사용하여 가중치를 기준으로 오름차순화한다 => 가장 낮은 cost 부터 Deque
- Queue에서 poll한 노드의 index에 해당하는 cost와 현재기록되어있는 dist배열의 index의 cost와 비교하여 방문처리
		현재 꺼낸 노드의 가중치가 dist의 가중치보다 크다면 해당 노드는 이전에 방문된 노드임
  boolean visited[] 대신 사용
- 출발역과 도착역의 id를 입력받으면 Arrival Repository를 통해 다음의 쿼리문을 실행 후 최단경로에 대한 정보를 response 함
  ```sql
  SELECT station_name, arrival_time
  FROM station S, arrival A
  WHERE A.train_id = (
	 SELECT A.train_id FROM arrival A, train T WHERE A.station_id = 115
     AND end_station_id = 134 AND A.train_id = T.train_id
     AND arrival_time > curtime() ORDER BY arrival_time ASC LIMIT 1)
  AND A.station_id IN (115, 119) AND A.station_id = S.station_id;
  ```
<br>

- 최단경로 탐색 구현 정확도 확인을 위한 비교영상

<div align="center">
	
<h2 align="left">　　　　　🚃 Metronom</h2>

![metronom_clip-ezgif com-speed](https://github.com/user-attachments/assets/04a683b5-27dd-4b83-9273-c54d6e12881c)

<h2 align="left">　　　　　🚋 Humetro<a href="https://www.humetro.busan.kr/homepage/cyberstation/map.do">(부산교통공사제공 노선검색 서비스)</a></h2>

![humetro_clip-ezgif com-speed](https://github.com/user-attachments/assets/236bce85-8742-46e3-96a6-34e85065fd75)

<h2 align="left">　　　　　🎯 Result</h2>

| Metronom | Humetro |
| :------: | :-----: |
| ![image](https://github.com/user-attachments/assets/aae5093d-42c1-4e5f-afad-31b9cee9d9d0) | ![image](https://github.com/user-attachments/assets/3ef30f8f-26ef-4f68-b2fb-117651254a18) |
<br>
</div>

- Reference
	- [[Youtube] "Java 다익스트라(dijkstra)", ezsx](https://www.youtube.com/watch?v=QleeV_ipB7w&t=1036s)
	- [[Tistory] "다익스트라 알고리즘(Dijkstra Algorithm)", sskl660](https://sskl660.tistory.com/59)
	- [[Tistory] "다익스트라(Dijkstra) 알고리즘을 Java로 구현해보자!", coding-knowjam](https://codingnojam.tistory.com/46)
<br>

### 8. 프로젝트 회고
	
- 배운점
  - 최단경로 탐색 알고리즘 구현  
    유튜브나 블로그 등을 통해 dijkstra에 대해 공부하고 프로젝트에 맞게 적용시켜보면서 그래프 이론에 대한 이해를 깊게 할 수 있었고 List, Map, PriorityQueue 등의 클래스를 사용하는 방법론에 대해서도 많이 익힐 수 있었다.
  - query 로직에 대한 이해도 향상  
    현재시간 보다 높은(지난) 시간 데이터를 찾기 위해 arrival_time > curtime()을 쓰고 해당하는 시간 데이터를 가지고 있는 train_id를 찾고 그 train_id가 도착하는 station_id를 또 찾아야되고... 이런 문제들을 MySQL에서
    반복해서 만지다보니 결과값을 도출하기 위한 최적의 query 로직을 설계하는것에 대해 감을 잡을 수 있었다.  
  - CSV 데이터 가공 프로그래밍(java)  
    공공데이터 포탈에서 받은 데이터들을 전처리 후 DB에 넣어야 했는데 과도하게 많은 양이라서 수작업을 할 순 없었고(할 순 있지만 시간이 없었고) 구글링을 통해 java에서 csv파일을 읽어들여 입맛에 맞게 가공할 수 있는 방법을 찾아 편리하게 데이터 전처리를 할 수 있었다.

- 아쉬운점
  - 실제 데이터(CCTV 이미지) 부재  
    처음 공모전에 출품할 생각으로 프로젝트를 구상했었는데 부산교통공사에서 CCTV 데이터를 반출할 수 없다는 입장을 들었다. 그래서 공모전에는 아이디어 부문으로 참가를 하여 아이디어 계획서만 제출하였고 실제 프로젝트에는 dummy data를 사용하여 구현을 했는데 이부분이
    너무 아쉬웠고 컴퓨터 비전은 따로 공부를 하여 이미지 분석 관련한 프로젝트를 실행해 볼 계획이다.  
  - Spring-Security  
    프로젝트를 계획할 당시 로그인 기능은 굳이 필요없을 것 같아서 포함을 안했었는데 프로젝트 후반쯤 그래도 배운거 써먹어보자 라는 생각으로 로그인, 회원가입 등의 기능을 만들고 JWT 토큰방식 인증까지 구현을 했었다. 하지만 로그인 후 그에 따르는 부가적인
    기능들(마이페이지, 게시판, 서비스 평가 페이지 등) 까지 구현하기에는 프로젝트 마감시간이 촉박했고 결국 프로젝트 발표때는 로그인 기능을 제외했었다. 개인적으로 멤버쉽 게시판 서비스 프로젝트를 만들어서 security를 사용한 로그인 및 부가적인 기능까지 완벽하게 구현해볼 계획이다.  
  - 프로젝트 과정 기록의 부재  
    프로젝트를 진행하며 마주친 여러가지 에러들이 많았고 무수한 디버깅 과정들이 있었지만 시간에 쫓기느라 기능을 구현하는 데에만 집중한 나머지 제대로 트러블슈팅 과정을 기록하지 못한게 가장 큰 아쉬운 점이다. 회고를 쓰면서 가장 강하게 느낀점이 그런 트러블슈팅 과정의
    기록들이야 말로 나의 피가 되고 살이되는 내 성장의 자양분이라는 것을 깨달은 것이다.

- 총평  
  코딩의 '코'자도 모르는 상태에서 약 4개월간 정말 압축적으로 java, java script, html, css, react 등을 머리속에 욱여넣다 싶이 배운다음에 맞이 한 첫 프로젝트이다. 배운점도 많고 아쉬운점도 많았으며 정말 어려웠고 너무 재미있는 시간이었다.
  Front-end와 Back-end가 어떻게 데이터를 주고받는지에 대한 흐름을 이해할 수 있게된 것이 가장 큰 수확이었고, 흐름을 이해한 후 Front-end를 맡은 팀원과의 소통이 더욱 원활해졌고 더 많은 아이디어가 나오고 더 빨리 문제해결을 하는 과정들이 즐거웠다.
  아직 배워야하고 경험해야 하는 부분이 너무 많이 남아있다는 사실이 나를 긴장하게 하고 들뜨게 한다. 다음 프로젝트가 기대된다.
  
