## Tmap API를 활용한 공유 퍼스널 모빌리티 서비스   
#### API 및 Sample Code 출처 : https://tmapapi.sktelecom.com/
#### 프로젝트 출처 : 한국산업기술대학교 소프트웨어공학과, 컴퓨터 공학과   
#### API 변경 일자 : 2020-07-20(월) [KakaoMap API -> Tmap API]   

- 이형석(16학번, 소프트웨어)   
		<span style="color:#FF0000">branch : dev</span>

	- 박세경(17학번, 컴퓨터공학과)   
				* branch : psk
	- 조현민(16학번, 소프트웨어)   
				* branch : HMC
	- 황규빈(16학번, 소프트웨어)   
				* branch : GBb


- 프로젝트   

	- 구성   

				* Kotlin(Android Studio)   
				* Tmap API   - with sample code   

	- 기간 7.13(월) ~ 8.12(수)   

	- 협업 도구   

				* Github   
				* Trello   
		
	- 소개   
				<https://github.com/leehyeongseck/KakaoMap-flow-coding/blob/master/Project_introduction.pdf>   
				
	- 개발 방법론   
	
				* 애자일 개발 방법론   

- 2020-07-31(금) 스프린트 0주차 및 백로그 설명   


	<https://github.com/leehyeongseck/TMap-flow-coding/blob/master/AgileReport_2020_07_31(FRI).pdf>   

	- 작성자 : 소프트웨어공학과 16학번 3학년 황규빈   

			- 소프트웨어공학과 16학번 3학년 조현민   
			- 컴퓨터공학과 17학번 3학년 박세경   
			- 소프트웨어공학과 16학번 3학년 이형석   

	- 목차   

			- 서론   
			- 애자일 개발의 이해   
			- 프로젝트 진행 프로세스   

					- 프로젝트 기획   
					- 스프린트 0   
					- 스프린트 1   

	- 사용 협업 도구   

			- Trello   
			- Git   
			- Github   

	- 스프린트 0 기간 : 2020-07-20(월) ~ 2020-08-02(일)   


- 2020-08-14(금) 스프린트 1주차 백로그 설명 및 최종 발표   

	- 스프린트 1 기간 : 2020-08-03(월) ~ 2020-08-14(금)   

	- 제품 시연 영상 : <https://www.youtube.com/watch?v=ktHRSrwHqg4>   

	- Trello : <https://trello.com/b/kNsqNjrU/agile-scrum-project-management2020-07-20-2020-08-02>   

	- 최종 발표 자료 : <https://github.com/leehyeongseck/TMap-flow-coding/blob/master/1%ED%8C%80%20%EC%B5%9C%EC%A2%85%20%EB%B0%9C%ED%91%9C.pdf>   
			- 작성자 : 조현민(16), 박세경(17)

	- 작업 공수표 : <https://github.com/leehyeongseck/TMap-flow-coding/blob/master/%EC%9E%91%EC%97%85%EA%B3%B5%EC%88%98.pdf>   


#### 제품 최종 기능   
		1. 맵의 트래킹 모드, 확대 및 축소   
		2. 현재 위치로 이동   
		3. 현재 위치를 기반으로 바라보는 방향 표시   
		4. 주소 입력을 통한 출발지, 목적지 등록 기능   
		5. 주소 검색 시 검색 결과 도출   
		6. 출발지, 목적지 마킹을 통한 길찾기 기능, 폴리건 라인으로 확인   
		7. 마커에 메뉴를 삽입하여 출발지, 목적지 등록 가능   
		8. 사용자 회원가입 시 운전면허, 아이디, 패스워드 등을 Firebase Realtime DB에 등록   
		9. 로그인 시 사용자 입력, DB를 비교하여 결과 값 반환   
		10. 구글, 페이스북 로그인 가능(API, Firebase 사용)   
		11. 대여 가능한 모빌리티 수단들을 화면에 표시   
		12. 현재 위치와 이용 가능한 모빌리티 간의 거리를 마커의 서브 타이틀에 표시, 현위치 갱신 시 서브 타이틀 변화   
		13. Firebase Realtime DB에 등록된 전동 킥보드 들의 위, 경도에 따라 Map에 마커로 표시   
		14. 길찾기 서비스를 이용하기 위한 UI 구현   
		15. 출발지, 도착지 마커를 새로운 아이콘 이미지를 통해서 구분   
		16. 길찾기 경로를 맵에 띄운 후 출발지에 포커스   
		17. 로그인 이후 이용하기 버튼 클릭 시 QR 코드 스캐너로 인텐트 전환(촬영 시 해당 QR코드의 URL을 Toast로 출력)   

