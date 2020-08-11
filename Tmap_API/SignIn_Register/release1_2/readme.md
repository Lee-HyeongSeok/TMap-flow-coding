## * Tmap API를 이용한 로그인/회원가입 서비스 만들기   
#### 코드 출처 : https://github.com/leehyeongseck/TMap-flow-coding/tree/dev/Tmap_API/SignIn_Register/release1_2   
#### API 출처 : https://tmapapi.sktelecom.com/index.html   
#### 3차 릴리즈 날짜 : 2020-08-11(화)   

#### 변경 및 추가 내용   
		* 로그인 및 회원가입 시 대여하기 버튼 클릭 후 QR 코드 스캔 화면으로 넘어감   
		* 마커 클릭 시 "m" 표시   
		* 마커 클릭 시 현재 위치와 마커 사이 거리 지속적 갱신   


#### QR 코드 스캔 설정   
		* https://github.com/leehyeongseck/TMap-flow-coding/tree/dev/Tmap_API/QR_Code_Scanning   


#### QR 코드 스캔 소스   

```kotlin
	// onCreate() 내부 선언 
	var intentIntegrator:IntentIntegrator = IntentIntegrator(this)
             intentIntegrator.initiateScan()


// onCreate() 외부 선언 
// QR 코드 스캔 후 결과 데이터를 전달받는 부분 
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result:IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null){
            if(result.contents == null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Scanned"+result.contents, Toast.LENGTH_SHORT).show()
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
```