# 🚇​[Project] 부산시 지하철 실시간 혼잡도 알림 서비스
> [부산대학교 K-Digital Trainig] AI활용 빅데이터분석 풀스택웹서비스 SW 개발자 양성과정  
> 수강중 진행한 프로젝트
<br>

![metronom_index_page](https://github.com/user-attachments/assets/6e4bbf2c-da49-44c6-adde-a4bb4636a216)

[시연영상](https://www.youtube.com/watch?v=eH851DO0fqw)
<br>

## 목차
 1. [프로젝트 소개](#1-프로젝트-소개)
 2. [팀구성 및 개발환경](#2-팀구성-및-기술스택)
 3. [구현 기능](#3-구현-기능)
 4. [화면 설계서](#4-화면-설계서)
 5. [REST API 명세](#5-rest-api-명세)
 6. [ERD 명세](#6-erd-명세)
 7. [
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

### 7. 최단 경로 알고리즘 구현(Dijkstra Algorithm)
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

  static class Node {		// 다음 노드의 index와 그 노드로 가는데 필요한 cost(가중치)
	 	int dest;  // 다음 노드 index
		int cost;  // 가중치
		...
  ```
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

- #### 비교영상


