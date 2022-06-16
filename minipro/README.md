# 🎁 포켓몬 띠부띠부

포켓몬빵을 사 먹지 못한 사람들의 한을 풀어주기 위한 사이트

제작 기간 : 2022.06.19 ~ 2022.06.16 (7일)

# :information_desk_person: 팀원 소개

🦌 : **BACK-END (3명)**

 + 김민주 : 로그인, 회원가입, 인증(JWT) 구현

 + 김승찬 : 데이터베이스 크롤링, 검색 CRUD, 메인 페이지, 디테일 페이지 구현

 + 심규홍 : 댓글 CRUD, 좋아요 기능 구현

🎅 : **FRONT-END (1명)** <a href="https://github.com/jjugwen/pokemon_front">front-end github</a>

 + 이영주

# :dizzy: 핵심기능
> 1) 회원가입 / 로그인
 + JWT 인증 방식으로 로그인 구현
 + ID 중복확인, 각 필드별 유효성체크

> 2) 포켓몬 열람
 + 포켓몬 목록
 + 포켓몬 상세 조회
 + 포켓몬 좋아요

> 3) 댓글 CRUD
 +댓글 등록, 댓글 수정, 댓글 삭제

# :tv: 데모영상
<img src="https://img.shields.io/badge/YouTube-FF0000?style=flat&logo=YouTube&logoColor=white"/> https://youtu.be/JPpEf-CZooE

# :computer: 기술 스택 
#### Server 
  <img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=AmazonAWS&logoColor=white">
  
#### Framework
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"><img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white">
  
#### Language
  <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">
  
#### Database
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  
#### Tool
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"><img src="https://img.shields.io/badge/Git-00000?style=for-the-badge&logo=Git&logoColor=F05032]"/><img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white]"/>

# :key: 트러블 슈팅
> 1) 백엔드 - 로그인, 회원가입
 + 시큐리티 - 프론트와 연결하기 위해 폼 로그인을 비활성화 => 필터를 달아주어 result 값을 전달
 + 시큐리티를 사용하여 리액트 서버와 연동 시키는 게 어려움 => 방법을 알아보다 rest api를 사용하면서 세션을 같이 사용하는 것이 알맞지 않다고 판단되어 JWT 토큰으로 로그인 방식 변경
 + 로그인, 회원가입 화면에서는 JWT토큰이 필요하지 않음 => tokenfilter를 통해 "/commet" 와 "/like" 창구에서만 token 여부를 확인하도록 설정
 + 기존 JWT 구현 시 만료기한 없이 액세스 토큰만 구현 => 보안을 위해 만료기간을 정한 뒤 다시 토큰을 재발급 받을 수 있게 리프레시 토큰을 생성

> 2) 백엔드 - 검색
 + 검색기능 사용할때 검색결과 조회시 JSON Parse 에러가 뜨는부분 
 + @ALLArgsConstructor 를 사용했을때 에러가 떠서 어노테이션 기능학습후
 + SearchDto 클래스에 @NoArgsConstructor 를 사용하여 해결하였다
 + 생성자를 자동생성하는 어노테이션이다
 + 파라미터가 없는 생성자를 만들어준다

> 3) 백엔드 - 서버 배포
 + 백 서버 배포후 프론트 로컬환경에서 자원 접근시 오류 발생
 + -Access to XMLHttpRequest at 'http://13.124.220.124/' from origin 'http://localhost:3000' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
 + 응답헤더에 "Access-Control-Allow-Origin"의 내용이 없어서 CORS 정책에 막혔었는데, Java Servlet의 Filter 구현체를 통해 response.setHeader("Access-Control-Allow-Origin", "url")을 활용해서 문제를 해결하였다.
