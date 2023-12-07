# GIS-recommendation
지리기반 맛집 추천 웹 서비스

## 개발 기간
2023.10.31 ~ 2023.11.10

## 목차
1. [프로젝트 요구사항](#프로젝트-요구사항)
   1. [유저 스토리](#유저-스토리)
   2. [담당 역할](#담당-역할) 
2. [Study](#Study)
   1. [요구사항](#요구사항)
   2. [예상](#예상)
   3. [상황](#상황)
   4. [선택](#선택)
   5. [트러블 슈팅](#트러블-슈팅)
      1. [문제](#문제)
      2. [문제 해결](#문제-해결)
   6. [trade off](#trade-off)
   7. [추가 내용](#추가-내용)
   8. [낙관적 락 사용시 데드락 발생](#낙관적-락-사용시-데드락-발생)
   9. [비관적 락 사용시](#비관적-락-사용시)

## 프로젝트 요구사항
[🍣지리기반 맛집 추천 웹 서비스 요구사항](https://hyerijang.notion.site/ed13c3afee524dc38a6a86a29489a75e?pvs=4)

### 유저 스토리
- 유저는 본 사이트에 들어와 회원가입 및 내 위치를 지정한다.
- **A. 내 위치 기반 맛집추천 = (`내 주변보기`)**
    - `도보` 기준 `1km` 이내의 맛집을 추천한다.
    - `교통수단` 기준 `5km` 이내의 맛집을 추천한다.
- **B. 지역명 기준 맛집추천(`특정 지역 보기`)**
    - 지정한 `지명(시군구)` 중심위치 기준 `10km` 이내의 맛집을 추천한다.
- **C. 점심 추천 서비스**
    - 점심 추천 서비스 이용을 승락한 대상에게 매일 정오, 대상의 위치를 기준으로 원하는 유형(일식, 중식 등)의 가게를 3개씩 추천 해준다.
- A, B는 다양한 검색기준 (정렬, 필터링 등)으로 조회 가능하며 (`거리순`, `평점순` , `양식`, `중식`)
- 해당 맛집의 상세정보를 확인할 수 있다.

### 담당 역할
<table>
    <tr>
        <td><B>팀원</B></td>
        <td><B>요구 사항</B></td>
        <td><B>요구 사항 상세</B></td>
    </tr>
    <tr>
        <td>문채영</td>
        <td>맛집 조회</td>
        <td>맛집 조회, 시군구 목록, 맛집 상세정보</td>
    </tr>
    <tr>
        <td>권순한</td>
        <td>데이터 파이프라인</td>
        <td>데이터 수집, 전처리, 저장, 자동화</td>
    </tr>
    <tr>
        <td>김시은</td>
        <td>사용자</td>
        <td>사용자 회원가입, 로그인, 설정 업데이트, 정보 조회</td>
    </tr>
    <tr>
        <td>정지원</td>
        <td>평가</td>
        <td>평가 모델링, 평가 생성</td>
    </tr>
</table>

## Study
- 담당 분량의 볼륨이 작아서, 팀원과 이야기를 한 후에 Database Lock에 대해서 스터디하고 결과를 공유하였습니다.
### 요구사항
- 요구사항 쓰기 !!@!@!@!@!@
### 예상 
- 두 개의 테이블을 수정해야 하기 떄문에 데이터 `정합성 문제`가 발생할 것이라고 예상 하였습니다.
- 데이터 정합성을 지키기위해서 `Lock`을 사용해야겠다고 생각하였습니다.
### 상황
- 맛집 리스트가 `경기도` 인점, 네이버의 강남역의 음식점 리뷰도 음식점 별 `일당 최대 10개`가 넘는 곳을 찾아 볼 수 없었습니다.
- 현실적으로 대이터 정합성을 지킬 수 없는 경우가 `거의 없을 것`이라고 예상하였습니다.
### 선택
- `낙관적 락`을 사용하면서, 실패 후 어플리케이션에서 `AOP로 재시도`를 하는 선택을 하였습니다.
### 트러블 슈팅
#### 문제
- `낙관적 락`을 사용하고 나서 테스트가 `확률적`으로 `실패`하였습니다.
  <img width="488" alt="Screenshot 2023-12-07 at 4 29 47 PM" src="https://github.com/Preonboarding-I-Team/GIS-recommendation/assets/115606959/f9d12ba3-f9ff-4cab-a9ca-0270a647ef85">
  <img width="488" alt="Screenshot 2023-12-07 at 4 29 57 PM" src="https://github.com/Preonboarding-I-Team/GIS-recommendation/assets/115606959/daf11e7f-636e-4647-9919-693116dc5fc4">
- 테스트가 실패하는 경우가 `2가지` 였습니다.
  1. `version`이 일치하지 않는 경우
     <img width="600" alt="Screenshot 2023-12-07 at 4 37 56 PM" src="https://github.com/Preonboarding-I-Team/GIS-recommendation/assets/115606959/78421f98-c882-4204-acc8-2aa4de3fc006">
     <img width="600" alt="Screenshot 2023-12-07 at 4 37 53 PM" src="https://github.com/Preonboarding-I-Team/GIS-recommendation/assets/115606959/1673d480-29e4-44ca-9f06-44ada6eae6d1">
  2. `rating`이 일치하지 않는 경우
     <img width="600" alt="Screenshot 2023-12-07 at 4 37 50 PM" src="https://github.com/Preonboarding-I-Team/GIS-recommendation/assets/115606959/6a819ccd-921f-43cd-b49b-4804ee11c459">
#### 문제 해결
- mysql의 로그를 살펴보니 특정 상황에서 `update` 쿼리가 `누락`된다는 것이 확인 되었습니다.
  <details>
  <summary>[클릭] MySQL 로그 보기</summary>
  
  - 데이터 init
  ```
  2023-12-07T07:41:27.524828Z       130 Query     SET autocommit=0
  2023-12-07T07:41:27.545127Z       130 Query     insert into user (latitude, longitude, lunch_service, password, role, username) values ('lat#1', 'log#1', 0, 'password#1', 'MEMBER', 'username#1')
  2023-12-07T07:41:27.564938Z       130 Query     commit
  2023-12-07T07:41:27.574094Z       130 Query     SET autocommit=1
  2023-12-07T07:41:27.577547Z       130 Query     SET autocommit=0
  2023-12-07T07:41:27.585251Z       130 Query     insert into store (bizplc_nm, bsn_state_nm, bsnsite_circumfr_div_nm, clsbiz_de, female_enflpsn_cnt, grad_div_nm, grad_faclt_div_nm, licensg_de, locplc_ar, male_enflpsn_cnt, multi_use_bizestbl_yn, rating, refine_lotno_addr, refine_roadnm_addr, refine_wgs84lat, refine_wgs84logt, refine_zip_cd, sanittn_bizcond_nm, sanittn_indutype_nm, sigun_nm, store_id, tot_emply_cnt, tot_faclt_scale, version, yy) values ('no info', 'no info', 'no info', 'open', 0, 'no info', 'no info', '2016-08-26', 0.0, 0, 'no info', 0.0, 'no info', 'no info', 0.0, 0.0, 'no info', 'no info', 'no info', 'no info', 'nullnull', 0.0, 0.0, 0, 0)
  ```
  - 테스트 시작
  - 데이터 조회
  ```
  2023-12-07T07:41:27.590108Z       130 Query     commit
  2023-12-07T07:41:27.592144Z       130 Query     SET autocommit=1
  2023-12-07T07:41:27.599145Z       130 Query     SET autocommit=0
  2023-12-07T07:41:27.599866Z       134 Query     SET autocommit=0
  2023-12-07T07:41:27.599866Z       133 Query     SET autocommit=0
  2023-12-07T07:41:27.599866Z       132 Query     SET autocommit=0
  2023-12-07T07:41:27.599873Z       131 Query     SET autocommit=0
  2023-12-07T07:41:27.652336Z       132 Query     select ...
  2023-12-07T07:41:27.653604Z       131 Query     select ...
  2023-12-07T07:41:27.653705Z       134 Query     select ...
  2023-12-07T07:41:27.654570Z       133 Query     select ...
  2023-12-07T07:41:27.654918Z       130 Query     select ...
  ```
  - 리뷰 등록
  ```
  2023-12-07T07:41:27.666595Z       132 Query     insert into review ...
  2023-12-07T07:41:27.666595Z       131 Query     insert into review ...
  2023-12-07T07:41:27.666595Z       130 Query     insert into review ...
  2023-12-07T07:41:27.666604Z       134 Query     insert into review ...
  2023-12-07T07:41:27.666605Z       133 Query     insert into review ...
  ```
  - update 쿼리
  ```
  2023-12-07T07:41:27.673800Z       134 Query     update store set ...
  2023-12-07T07:41:27.673800Z       133 Query     update store set ...
  2023-12-07T07:41:27.673813Z       132 Query     update store set ...
  2023-12-07T07:41:27.673830Z       130 Query     update store set ...
  2023-12-07T07:41:27.674011Z       131 Query     update store set ...
  ```
  - 첫번째 성공 (커밋 1, 롤백 4)
  ```
  2023-12-07T07:41:27.679164Z       134 Query     commit
  2023-12-07T07:41:27.681132Z       134 Query     SET autocommit=1
  2023-12-07T07:41:27.684122Z       131 Query     rollback
  2023-12-07T07:41:27.684138Z       133 Query     rollback
  2023-12-07T07:41:27.684143Z       130 Query     rollback
  2023-12-07T07:41:27.684154Z       132 Query     rollback
  2023-12-07T07:41:27.684644Z       131 Query     SET autocommit=1
  2023-12-07T07:41:27.684861Z       133 Query     SET autocommit=1
  2023-12-07T07:41:27.684873Z       130 Query     SET autocommit=1
  2023-12-07T07:41:27.684874Z       132 Query     SET autocommit=1
  2023-12-07T07:41:27.686598Z       131 Query     SET autocommit=0
  2023-12-07T07:41:27.686598Z       133 Query     SET autocommit=0
  2023-12-07T07:41:27.686600Z       130 Query     SET autocommit=0
  2023-12-07T07:41:27.686603Z       132 Query     SET autocommit=0
  ```
  - 조회 - 2번째 시도
  ```
  2023-12-07T07:41:27.689794Z       131 Query     select ...
  2023-12-07T07:41:27.689795Z       132 Query     select ...
  2023-12-07T07:41:27.689802Z       130 Query     select ...
  2023-12-07T07:41:27.690068Z       133 Query     select ...
  ```
  - 리뷰 등록 - 2번째 시도
  ```
  2023-12-07T07:41:27.696149Z       130 Query     insert into review ...
  2023-12-07T07:41:27.696197Z       131 Query     insert into review ...
  2023-12-07T07:41:27.696175Z       132 Query     insert into review ...
  2023-12-07T07:41:27.696149Z       133 Query     insert into review ...
  ```
  - **커밋 ?**
  ```
  2023-12-07T07:41:27.697675Z       131 Query     commit
  ```
  - update 쿼리 - 2번째 시도
  ```
  2023-12-07T07:41:27.698779Z       133 Query     update store set ...
  2023-12-07T07:41:27.698799Z       132 Query     update store set ...
  2023-12-07T07:41:27.698959Z       130 Query     update store set ...
  ```
  - **두번째 성공 (커밋 1, 롤백2) ?**
  ```
  2023-12-07T07:41:27.700175Z       131 Query     SET autocommit=1
  2023-12-07T07:41:27.700689Z       132 Query     commit
  2023-12-07T07:41:27.701499Z       133 Query     rollback
  2023-12-07T07:41:27.701632Z       130 Query     rollback
  2023-12-07T07:41:27.702284Z       133 Query     SET autocommit=1
  2023-12-07T07:41:27.702427Z       130 Query     SET autocommit=1
  2023-12-07T07:41:27.703652Z       132 Query     SET autocommit=1
  2023-12-07T07:41:27.703725Z       133 Query     SET autocommit=0
  2023-12-07T07:41:27.703743Z       130 Query     SET autocommit=0
  ```
  - 조회 - 3번째 시도
  ```
  2023-12-07T07:41:27.705993Z       133 Query     select ...
  2023-12-07T07:41:27.706004Z       130 Query     select ...
  ```
  - 리뷰 등록 - 3번째 시도
  ```
  2023-12-07T07:41:27.708858Z       130 Query     insert into review ...
  2023-12-07T07:41:27.708858Z       133 Query     insert into review ...
  ```
  - 커밋 ?: update 쿼리 없음
  ```
  2023-12-07T07:41:27.709958Z       133 Query     commit
  ```
  - update 쿼리
  ```
  2023-12-07T07:41:27.710527Z       130 Query     update store set ...
  ```
  - 3번째 성공
  ```
  2023-12-07T07:41:27.712002Z       133 Query     SET autocommit=1
  2023-12-07T07:41:27.712027Z       130 Query     commit
  2023-12-07T07:41:27.713922Z       130 Query     SET autocommit=1
  ```
  - 검증 조회 ()
  ```
  2023-12-07T07:41:27.716072Z       130 Query     set session transaction read only
  2023-12-07T07:41:27.716706Z       130 Query     SET autocommit=0
  2023-12-07T07:41:27.723221Z       130 Query     select ...
  2023-12-07T07:41:27.725084Z       130 Query     commit
  2023-12-07T07:41:27.725686Z       130 Query     SET autocommit=1
  2023-12-07T07:41:27.726263Z       130 Query     set session transaction read write
  2023-12-07T07:41:27.787975Z       130 Quit
  2023-12-07T07:41:27.788091Z       138 Quit
  2023-12-07T07:41:27.788161Z       133 Quit
  2023-12-07T07:41:27.788172Z       137 Quit
  2023-12-07T07:41:27.788198Z       135 Quit
  2023-12-07T07:41:27.788211Z       139 Quit
  2023-12-07T07:41:27.788224Z       134 Quit
  2023-12-07T07:41:27.788282Z       136 Quit
  2023-12-07T07:41:27.788292Z       131 Quit
  2023-12-07T07:41:27.788301Z       132 Quit
  ```
  </details>

- 값을 추적해보니 `store`에서 `rating`의 값이 변하지 않으면(ex: 3.0 -> 3.0) `dirty checking`이 일어나지 않아 `update` 하지 않는 상황이었습니다.
- `version`값을 변경하여 어떤 상황에서도 `update`가 발생하도록 수정하였습니다.
### trade off
- 업데이트가 필요 없는 상황에서도 업데이트 쿼리가 발생합니다.
- 데이터의 정합성을 지킬 수 있었습니다.
### 추가 내용
#### 낙관적 락 사용시 데드락 발생
- mysql 로그를 살펴보던중 데드락 발생으로 `version`의 값이 달라서 `update`가 안된다기 보다는 `deadlock` 발생으로 업데이트가 안되는 것이 확인 되었습니다.
  - 데드락 로그
    - 내용 요약
      - tx1: s lock 획득
      - tx1: x lock 획득 대기
      - tx2: s lock 획득
      - tx2: x lock 획득 대기 ( -> deadlock )
      - tx2 -> rollback 결정
  - 로그
    ```
    ------------------------
    LATEST DETECTED DEADLOCK
    ------------------------
    2023-11-05 13:45:37 281473153331008
    *** (1) TRANSACTION:
    TRANSACTION 101016, ACTIVE 0 sec starting index read
    mysql tables in use 1, locked 1
    LOCK WAIT 7 lock struct(s), heap size 1128, 3 row lock(s), undo log entries 1
    MySQL thread id 918, OS thread handle 281473098747712, query id 14182 172.17.0.1 hello updating
    update store set bizplc_nm='no info', bsn_state_nm='no info', bsnsite_circumfr_div_nm='no info', clsbiz_de='open', female_enflpsn_cnt=0, grad_div_nm='no info', grad_faclt_div_nm='no info', licensg_de='2016-08-26', locplc_ar=0.0, male_enflpsn_cnt=0, multi_use_bizestbl_yn='no info', rating=3.0, refine_lotno_addr='no info', refine_roadnm_addr='no info', refine_wgs84lat=0.0, refine_wgs84logt=0.0, refine_zip_cd='no info', sanittn_bizcond_nm='no info', sanittn_indutype_nm='no info', sigun_nm='no info', store_id='nullnull', tot_emply_cnt=0.0, tot_faclt_scale=0.0, version=4, yy=0 where id=1 and version=3
    
    *** (1) HOLDS THE LOCK(S):
    RECORD LOCKS space id 1922 page no 4 n bits 72 index PRIMARY of table `mysql-db`.`store` trx id 101016 lock mode S locks rec but not gap
    Record lock, heap no 2 PHYSICAL RECORD: n_fields 28; compact format; info bits 0
     0: len 8; hex 8000000000000001; asc         ;;
     1: len 6; hex 000000018a94; asc       ;;
     2: len 7; hex 020000018f0151; asc       Q;;
     3: len 7; hex 6e6f20696e666f; asc no info;;
     4: len 7; hex 6e6f20696e666f; asc no info;;
     5: len 7; hex 6e6f20696e666f; asc no info;;
     6: len 4; hex 6f70656e; asc open;;
     7: len 4; hex 80000000; asc     ;;
     8: len 7; hex 6e6f20696e666f; asc no info;;
     9: len 7; hex 6e6f20696e666f; asc no info;;
     10: len 3; hex 8fc11a; asc    ;;
     11: len 8; hex 0000000000000000; asc         ;;
     12: len 4; hex 80000000; asc     ;;
     13: len 7; hex 6e6f20696e666f; asc no info;;
     14: len 8; hex 0000000000000840; asc        @;;
     15: len 7; hex 6e6f20696e666f; asc no info;;
     16: len 7; hex 6e6f20696e666f; asc no info;;
     17: len 8; hex 0000000000000000; asc         ;;
     18: len 8; hex 0000000000000000; asc         ;;
     19: len 7; hex 6e6f20696e666f; asc no info;;
     20: len 7; hex 6e6f20696e666f; asc no info;;
     21: len 7; hex 6e6f20696e666f; asc no info;;
     22: len 7; hex 6e6f20696e666f; asc no info;;
     23: len 8; hex 6e756c6c6e756c6c; asc nullnull;;
     24: len 8; hex 0000000000000000; asc         ;;
     25: len 8; hex 0000000000000000; asc         ;;
     26: len 4; hex 80000003; asc     ;;
     27: len 4; hex 80000000; asc     ;;
    
    
    *** (1) WAITING FOR THIS LOCK TO BE GRANTED:
    RECORD LOCKS space id 1922 page no 4 n bits 72 index PRIMARY of table `mysql-db`.`store` trx id 101016 lock_mode X locks rec but not gap waiting
    Record lock, heap no 2 PHYSICAL RECORD: n_fields 28; compact format; info bits 0
     0: len 8; hex 8000000000000001; asc         ;;
     1: len 6; hex 000000018a94; asc       ;;
     2: len 7; hex 020000018f0151; asc       Q;;
     3: len 7; hex 6e6f20696e666f; asc no info;;
     4: len 7; hex 6e6f20696e666f; asc no info;;
     5: len 7; hex 6e6f20696e666f; asc no info;;
     6: len 4; hex 6f70656e; asc open;;
     7: len 4; hex 80000000; asc     ;;
     8: len 7; hex 6e6f20696e666f; asc no info;;
     9: len 7; hex 6e6f20696e666f; asc no info;;
     10: len 3; hex 8fc11a; asc    ;;
     11: len 8; hex 0000000000000000; asc         ;;
     12: len 4; hex 80000000; asc     ;;
     13: len 7; hex 6e6f20696e666f; asc no info;;
     14: len 8; hex 0000000000000840; asc        @;;
     15: len 7; hex 6e6f20696e666f; asc no info;;
     16: len 7; hex 6e6f20696e666f; asc no info;;
     17: len 8; hex 0000000000000000; asc         ;;
     18: len 8; hex 0000000000000000; asc         ;;
     19: len 7; hex 6e6f20696e666f; asc no info;;
     20: len 7; hex 6e6f20696e666f; asc no info;;
     21: len 7; hex 6e6f20696e666f; asc no info;;
     22: len 7; hex 6e6f20696e666f; asc no info;;
     23: len 8; hex 6e756c6c6e756c6c; asc nullnull;;
     24: len 8; hex 0000000000000000; asc         ;;
     25: len 8; hex 0000000000000000; asc         ;;
     26: len 4; hex 80000003; asc     ;;
     27: len 4; hex 80000000; asc     ;;
    
    
    *** (2) TRANSACTION:
    TRANSACTION 101017, ACTIVE 0 sec starting index read
    mysql tables in use 1, locked 1
    LOCK WAIT 7 lock struct(s), heap size 1128, 3 row lock(s), undo log entries 1
    MySQL thread id 916, OS thread handle 281473102974784, query id 14183 172.17.0.1 hello updating
    update store set bizplc_nm='no info', bsn_state_nm='no info', bsnsite_circumfr_div_nm='no info', clsbiz_de='open', female_enflpsn_cnt=0, grad_div_nm='no info', grad_faclt_div_nm='no info', licensg_de='2016-08-26', locplc_ar=0.0, male_enflpsn_cnt=0, multi_use_bizestbl_yn='no info', rating=3.0, refine_lotno_addr='no info', refine_roadnm_addr='no info', refine_wgs84lat=0.0, refine_wgs84logt=0.0, refine_zip_cd='no info', sanittn_bizcond_nm='no info', sanittn_indutype_nm='no info', sigun_nm='no info', store_id='nullnull', tot_emply_cnt=0.0, tot_faclt_scale=0.0, version=4, yy=0 where id=1 and version=3
    
    *** (2) HOLDS THE LOCK(S):
    RECORD LOCKS space id 1922 page no 4 n bits 72 index PRIMARY of table `mysql-db`.`store` trx id 101017 lock mode S locks rec but not gap
    Record lock, heap no 2 PHYSICAL RECORD: n_fields 28; compact format; info bits 0
     0: len 8; hex 8000000000000001; asc         ;;
     1: len 6; hex 000000018a94; asc       ;;
     2: len 7; hex 020000018f0151; asc       Q;;
     3: len 7; hex 6e6f20696e666f; asc no info;;
     4: len 7; hex 6e6f20696e666f; asc no info;;
     5: len 7; hex 6e6f20696e666f; asc no info;;
     6: len 4; hex 6f70656e; asc open;;
     7: len 4; hex 80000000; asc     ;;
     8: len 7; hex 6e6f20696e666f; asc no info;;
     9: len 7; hex 6e6f20696e666f; asc no info;;
     10: len 3; hex 8fc11a; asc    ;;
     11: len 8; hex 0000000000000000; asc         ;;
     12: len 4; hex 80000000; asc     ;;
     13: len 7; hex 6e6f20696e666f; asc no info;;
     14: len 8; hex 0000000000000840; asc        @;;
     15: len 7; hex 6e6f20696e666f; asc no info;;
     16: len 7; hex 6e6f20696e666f; asc no info;;
     17: len 8; hex 0000000000000000; asc         ;;
     18: len 8; hex 0000000000000000; asc         ;;
     19: len 7; hex 6e6f20696e666f; asc no info;;
     20: len 7; hex 6e6f20696e666f; asc no info;;
     21: len 7; hex 6e6f20696e666f; asc no info;;
     22: len 7; hex 6e6f20696e666f; asc no info;;
     23: len 8; hex 6e756c6c6e756c6c; asc nullnull;;
     24: len 8; hex 0000000000000000; asc         ;;
     25: len 8; hex 0000000000000000; asc         ;;
     26: len 4; hex 80000003; asc     ;;
     27: len 4; hex 80000000; asc     ;;
    
    
    *** (2) WAITING FOR THIS LOCK TO BE GRANTED:
    RECORD LOCKS space id 1922 page no 4 n bits 72 index PRIMARY of table `mysql-db`.`store` trx id 101017 lock_mode X locks rec but not gap waiting
    Record lock, heap no 2 PHYSICAL RECORD: n_fields 28; compact format; info bits 0
     0: len 8; hex 8000000000000001; asc         ;;
     1: len 6; hex 000000018a94; asc       ;;
     2: len 7; hex 020000018f0151; asc       Q;;
     3: len 7; hex 6e6f20696e666f; asc no info;;
     4: len 7; hex 6e6f20696e666f; asc no info;;
     5: len 7; hex 6e6f20696e666f; asc no info;;
     6: len 4; hex 6f70656e; asc open;;
     7: len 4; hex 80000000; asc     ;;
     8: len 7; hex 6e6f20696e666f; asc no info;;
     9: len 7; hex 6e6f20696e666f; asc no info;;
     10: len 3; hex 8fc11a; asc    ;;
     11: len 8; hex 0000000000000000; asc         ;;
     12: len 4; hex 80000000; asc     ;;
     13: len 7; hex 6e6f20696e666f; asc no info;;
     14: len 8; hex 0000000000000840; asc        @;;
     15: len 7; hex 6e6f20696e666f; asc no info;;
     16: len 7; hex 6e6f20696e666f; asc no info;;
     17: len 8; hex 0000000000000000; asc         ;;
     18: len 8; hex 0000000000000000; asc         ;;
     19: len 7; hex 6e6f20696e666f; asc no info;;
     20: len 7; hex 6e6f20696e666f; asc no info;;
     21: len 7; hex 6e6f20696e666f; asc no info;;
     22: len 7; hex 6e6f20696e666f; asc no info;;
     23: len 8; hex 6e756c6c6e756c6c; asc nullnull;;
     24: len 8; hex 0000000000000000; asc         ;;
     25: len 8; hex 0000000000000000; asc         ;;
     26: len 4; hex 80000003; asc     ;;
     27: len 4; hex 80000000; asc     ;;
    
    *** WE ROLL BACK TRANSACTION (2)
    ```
#### 비관적 락 사용시
- 단순히 5번(테스트 Retry 횟수) 실행되는 속도만 비교한다면, `@Retry`에서 잠시 쉬어주는 시간이 있기 떄문에 `비관적락`이 좀 더 빨랐습니다.
- 1번만 실행했을 떄는 크게 다를 점이 없고, **여전히 충돌이 일어날 가능성은 낮다고 판단**되어서 `낙관적 락`을 사용하였습니다.
