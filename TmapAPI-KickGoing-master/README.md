# TmapAPI-KickGoing

## Tmap API를 사용한 길찾기 기능이 포함된 퍼스널 모빌리티 대여 어플
#### - 프로젝트기간 : 2020.07.06 ~ 2020.08.14
#### - 주제 선정, 요구사항 분석, Product Backlog 작성기간 : 2020.07.06 ~ 2020.07.17
#### - Sprint 0 : 2020.07.20 ~ 2020.08.02
#### - Sprint 1 : 2020.08.03 ~ 2020.08.14
#### - 최종 결과물 데모영상 : https://www.youtube.com/watch?v=ktHRSrwHqg4&lc=UgwkSMx3J8RLqMpuJth4AaABAg


## 요구사항 분석

#### - 성능적 요구사항
```
1. 배터리 배터리 소모를 줄이기 위해 앱을 최소화 할 시 백그라운드에서는 동작하지 않게 한다.
2. 길찾기 시 검색 결과도출의 응답시간이 빨라야한다.
```

#### - 인터페이스 요구사항
```
1. 온라인 도움말, 사용 단말을 고려한 화면사이즈 제공, 오류메시지에 대한 내용을 제공하여
사용자 편의성 보장

2. 통합 UI를 구성하여 시스템 또는 SW의 통일성 보장
```

#### - 품질 요구사항
```
1. 시스템은 신속한 장애 대응을 위하여 백업 절차를 마련해야 함
2. 에러복구, 장애 대책 확보 등 신뢰성 있는 서비스 환경 제공
3. 프로그램 설치 및 제거, 이용이 용이하여야 함
```

#### - 기타 요구사항
```
1. 초기 자료 구축 시, 필요 작업 요소를 기술하고 작업 요소별 보정 요소에 대한 요건을 명시
2. 데이터 전환 시, 데이터 전환 시간, 전환 데이터의 우선순위 등을 명시해야 한다.
```

#### - 기능적 요구사항
```
1. 사용자는 전동 킥보드 기준의 길찾기 기능을 이용
2. 원하는 사용자는 공유 전동 킥보드 대여 서비스를 이용
3. 서비스 환경 제공
4. 프로그램 설치 및 제거, 이용이 용이
```

#### - 요구사항에 부합하는 기능
```
1. 사용자는 공유 전동 킥보드를 선택하여 이용.
2. 자신의 현재 위치를 기준으로 포커스를 위한 버튼이 존재, 버튼을
누를 시 자신의 현재 위치 중심의 맵을 보여줌
3. +버튼과 – 버튼이 존재하며, 이는 각각 줌인 줌 아웃 기능으로 맵을
확대 축소
4. 포커싱된 맵의 일정 반경내에 있는 전동 킥보드를 보여줌.
5. 사용자는 회원가입을 할 때 운전면허를 등록할 수 있음.
6. 사용자는 현재위치로부터 목적지까지의 폴리건 라인을 볼 수 있음.
7. 사용자는 기존의 길찾기 기능에 더해 전동 킥보드 기준의 길찾기 기능
을 이용.
8. 사용자는 현재위치로부터 자신의 정해진 반경 내 전동 킥보드까지의
거리, 예상시간을 알 수 있음.
9. 대여하기 버튼을 누르면 QR코드를 찍을 수 있는 화면이 나옴.
10. 전동 킥보드가 위치하는 곳에는 마커가 찍혀져 있다.
11. 전동 킥보드를 빌리게 되면 마커가 사라진다.
12. 나침반 버튼이 있고 버튼 클릭 시 방향에 맞게 지도가 회전하고 한번 더 클릭시
지도는 고정되어 터치와 드래그로 조작가능하다.
13. 각 전동킥보드 마킹은 각자 고유 태그값을 가지고 있다.
```

## * 구현할 기능 
    * 1. 앱을 실행시켰을 때 사용자는 현재 위치를 확인할 수 있어야 한다.
    * 2. 다른 지역확인 중 현재위치 버튼 클릭 시 현재 위치로 이동할 수 있어야 한다.
    * 3. 출발지, 목적지 입력 시 폴리건 라인을 통해 거리를 선으로 나타낸다.
    * 4. 전동 킥보드의 길찾기는 자전거와 동일한 수준으로 길찾기를 해준다.
    * 5. 사용자는 포커싱된 맵의 일정 반경내에 있는 전동 킥보드를 볼 수 있다.
    * 6. 전동 킥보드는 맵에 랜덤 좌표로 배정된다. (실제 전동 킥보드를 배치할 수 없기때문)
    * 7. 사용자는 일정 반경내에 있는 킥고잉과의 거리, 예상 시간을 알 수 있다.
    * 8. 전동 킥보드의 위치, 태그, 이름은 Firebase RealTime DB에 저장한다.
    * 9. 사용자는 회원가입을 할 때 선택적으로 운전면허 등록을 할 수 있다.
    * 10. 사용자는 전동 킥보드를 대여하기 위해서 회원가입 및 로그인을 해야하며 운전면허 등록이 필요하다.
    * 11. 사용자는 이용하기 버튼을 누르면 전동 킥보드에 부착되어 있는 QR코드를 찍어 대여할 수 있다.
    
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
    

# MainActivity
## * TMapView 띄우기와 현재 위치를 받기 위한 준비
```kotlin
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var lm : LocationManager
    private var isLocation = true
    private var actionBar = false
    private lateinit var tMapView:TMapView
    private var QR_on = false

    //var currentUser: FirebaseAuth = Activity_login.auth

    private var myLongitude: Double = 0.0 // 현재위치, 경도
    private var myLatitude: Double = 0.0   // 현재위치, 위도
    var data = ArrayList<TMapMarkerItem>() // 킥고잉 마커명, 위치 저장 해시맵
    var n=0

    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_format_list_bulleted_24)

        val intent: Intent = Intent(this, Activity_login::class.java)
        startActivity(intent)



        //val Rlayout:RelativeLayout = findViewById(R.id.map_view) as RelativeLayout
        tMapView = TMapView(this)
        tMapView!!.setSKTMapApiKey("API KEY")
//        map_view.addView(tMapView)  //레이아웃에 Tmap 추가
        tMapView!!.setIconVisibility(true)
        tMapView!!.setCompassMode(true)
        tMapView!!.setSightVisible(true)
```
## * 위치 변경되면 발생하는 이벤트리스너
```kotlin
private val mLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                myLongitude = location.longitude
                myLatitude = location.latitude

                tMapView!!.setLocationPoint(myLongitude, myLatitude)
                tMapView!!.setCenterPoint(myLongitude, myLatitude)
                tMapView!!.setTrackingMode(true)

                Thread(Runnable { // runOnUiThread를 추가하고 그 안에 UI작업을 한다.
                    runOnUiThread {
                        for(i in data){ // data에는 DB에서 받아온 킥고잉 마커들이 담겨있음
                            var lat = i.tMapPoint.latitude
                            var lon = i.tMapPoint.longitude
                            var model = i.calloutTitle

                            var p:TMapPoint = TMapPoint(lat, lon) // 마커 포인트

                            // 2020-08-11(화) 작성
                            // 현재 위치 변경 시 킥고잉과의 거리를 서브 타이틀에 표시
                            var myLocation = TMapPoint(location!!.latitude, location.longitude) // 현재위치를 가진 변수
                            var tpolyLine = TMapPolyLine()
                            tpolyLine.addLinePoint(p) // 마커 포인트 추가
                            tpolyLine.addLinePoint(myLocation) // 현재위치 포인트 추가
                            var dist = tpolyLine.distance.toInt()
                            i.calloutSubTitle = "${dist}m"
                            i.autoCalloutVisible = true
                            tMapView!!.invalidate()
                        }
                    }
                }).start()

            }
        } // end of mLocationListener
```

## * 현재 위치를 받기 위한 gps 함수 사용
```kotlin
    // 현재위치를 위한 gps를 받아오는 함수
    fun setGps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        lm.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,  // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
            500, 1f,  // 통지사이의 최소 변경거리 (m)
            mLocationListener
        )
    }// end of setGps()
```
## * gps버튼, 확대, 축소버튼에 대한 클릭 리스너
```kotlin
myLocation.setOnClickListener {
            if(isLocation){
                tMapView!!.setTrackingMode(false)
                tMapView!!.setSightVisible(false)
                tMapView!!.setCompassMode(false)
                tMapView!!.invalidate()
                isLocation = false
                myLocation.setImageResource(R.drawable.ic_baseline_my_location_24_black)
            }
            else{
                tMapView!!.setTrackingMode(true)
                tMapView!!.setSightVisible(true)
                tMapView!!.setCompassMode(true)
                isLocation = true
                myLocation.setImageResource(R.drawable.ic_baseline_my_location_24)
            }
        }

        //zoomIn버튼 클릭시 지도 확대
        zoomIn.setOnClickListener {
            tMapView?.MapZoomIn()
        }
        //zoomOut버튼 클릭시 지도 축소
        zoomOut.setOnClickListener {
            tMapView?.MapZoomOut()
        }
```

## * 앱이 실행될 때 User클래스의 정보를 가져와 로그인 상태를 체크한다.
```kotlin
 override fun onStart() {
        super.onStart()
        drawer_layout.closeDrawer(GravityCompat.START)

        var fb: FirebaseUser? = auth?.currentUser
        if (fb != null) {
            if (User.getFBUserLog() == true) {
                User.setName(fb.displayName.toString())
                User.setEmail("FaceBook")
                User.setUserLog(true)
            } else {
                User.setName(fb.displayName.toString())
                User.setEmail(fb.email.toString())
                User.setUserLog(true)
            }
        }
        Log.d("start", "email : " + User.getEmail())
        Log.d("start", "name : " + User.getName())
        Log.d("start", "login : " + User.getUserLog())
    }
```

## * 툴바의 메뉴를 클릭할 때 User클래스의 정보를 바탕으로 현재 로그인한 유저정보로 세팅해준다.
```kotlin
   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                name.text = User.getName()
                email.text = User.getEmail()
                Log.d("showInformed", "mainEmail: " + User.getName())
            }
        }
        return super.onOptionsItemSelected(item)
    }
```

## * 툴바의 메뉴를 눌렀을 때 나오는 메뉴들에 대한 행동
```kotlin
override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.account-> {
                if(User.getUserLog()==true){
                    auth.signOut()
                }
                else {
                    val intent: Intent = Intent(this, Activity_login::class.java)
                    startActivity(intent)
                }
            }
            R.id.how-> Toast.makeText(this,"item2 clicked",Toast.LENGTH_SHORT).show()
            R.id.setting-> Toast.makeText(this,"item3 clicked",Toast.LENGTH_SHORT).show()
            R.id.logout->{
                drawer_layout.closeDrawer(GravityCompat.START)
                Firebase.auth.signOut()
                User.setUserLog(false)
                User.setName("QuickGoing")
                User.setFBlogin(false)
                User.setEmail("do not login")
            }
        }
        return false
    }
```

## * Static 변수 선언
```kotlin
    companion object{
        var auth: FirebaseAuth = FirebaseAuth.getInstance()
    }
```

## * 이용하기 버튼 눌렀을 때 로그인 상태라면 QR코드찍는 화면, 로그인 상태가 아니라면 Activity_login액티비티로 넘어감
```kotlin
usingButton.setOnClickListener {
            Log.d("login", User.getUserLog().toString())
            if(User.getUserLog() == false){
                // login intent

                builder2.setIcon(R.drawable.kickgoing)
                builder2.setTitle("QucikGoing")
                builder2.show()
            }
            else if(User.getUserLog() == true){
                // qr
                var intentIntegrator:IntentIntegrator = IntentIntegrator(this)
                intentIntegrator.initiateScan()
                QR_on = true
            }


        } // end of usingButton Listener
```

# User Class
## * 사용자 로그인상태 및 정보를 저장하는 클래스
```kotlin
import com.facebook.login.Login
import com.google.firebase.auth.FirebaseAuth

object User {
    var Login:Boolean = false
    var facebookLogin:Boolean = false
    var UserName:String = "QuickGoing"
    var UserEmail:String = "로그인해라"

    fun setFBlogin(log:Boolean){
        facebookLogin = log
    }

    fun setUserLog(log: Boolean){
        Login = log
    }
    fun setName(name:String){
        UserName = name
    }
    fun setEmail(email:String){
        UserEmail = email
    }


    fun getName(): String {
        return UserName
    }
    fun getEmail(): String {
        return UserEmail
    }
    fun getUserLog(): Boolean{
        return Login
    }
    fun getFBUserLog():Boolean{
        return facebookLogin
    }
}
```

# RegisterEventActivity
## * 초기 확인버튼은 클릭 불가능한 상태 -> 중복체크에서 요구조건이 만족되면 확인버튼 클릭 가능
```kotlin
// 확인버튼 누를 시 동작
        registID.setOnClickListener {
            if(pass.text.toString() == ""){
                Toast.makeText(applicationContext, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                // 입력된 아이디, 패스워드, 면허 등록
                val u_id: String = id.text.toString()
                val u_pass: String = pass.text.toString()
                val u_drive: String = drive.text.toString()
                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                val myRef: DatabaseReference = database.getReference("user")
                var data = HashMap<String, String>()
                data.put("password", u_pass)
                data.put("drive_pass", u_drive)
                myRef.child(u_id).setValue(data)
                finish()
            }
        }


        // 중복 체크 버튼
        check_Btn.setOnClickListener {
            val u_id:String = id.text.toString()
            val u_pass:String = pass.text.toString()
            val u_drive:String = drive.text.toString()
            val database:FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef:DatabaseReference = database.getReference("user")

            // DB에 이벤트 발생 시 동작하는 함수
            myRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(list in snapshot.children){
                        if(u_id.equals(list.key)){ // 사용자 입력 아이디가 하나라도 중복될 경우
                            checking_id = true // 중복이라는 표시
                        }
                    }
                    /*
                    if(checking_id == true){
                        Toast.makeText(applicationContext, "아이디 중복", Toast.LENGTH_SHORT).show()
                        checking_id = false
                    }
                    else{ // false일 떄
                        Toast.makeText(applicationContext, "사용 가능 아이디", Toast.LENGTH_SHORT).show()
                        registID.isClickable = true
                    }
                     */
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
            if(checking_id == true){
                Toast.makeText(applicationContext, "아이디 중복", Toast.LENGTH_SHORT).show()
            }
            else{ // false일 떄
                Toast.makeText(applicationContext, "사용 가능 아이디", Toast.LENGTH_SHORT).show()
                registID.isClickable = true
            }
        }
```

# Activity_login
## * FireBase에 사용자 등록(초기에 1번만 실행)
```kotlin
private fun registerFB(){
        var auth2 = Firebase.auth
        auth2.signInWithEmailAndPassword("Email", "password")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    val user = auth2.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
```

## * RegisterEventActivity에서 회원가입을 통해 Firebase에 데이터를 저장하고 이 데이터를 바탕으로 로그인 처리
```kotlin
btnLogin.setOnClickListener {
            val str_id: String = etEmail.text.toString()
            val str_pass: String = etPassword.text.toString()

            // firebase realtime DB에 접근하는 부분
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef: DatabaseReference = database.getReference("user")

            // DB에 접근해서 로그인한 아이디와 비밀번호 일치성 검사, 운전면허 여부 검사
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (list in snapshot.children) {
                        if (str_id.equals(list.key)) { // 사용자 입력 아이디가 하나라도 중복될 경우
                            checking_id = true // 중복이라는 표시
                        }
                    }
                    if (checking_id == true) { // 존재하는 아이디
                        // 해당 아이디의 비밀번호를 DB에서 가져온다.
                        var temp: String = snapshot.child(str_id).child("password").getValue() as String
                        // 입력한 비밀번호와 DB에서 가져온 비밀번호 일치 시
                        if (str_pass.equals(temp)) { // 로그인 성공
                            Toast.makeText(applicationContext, "${str_id} 님이 로그인하셨습니다.", Toast.LENGTH_SHORT).show()

                           // User.setName(str_id)
                            User.setName(str_id.toString())
                            User.setEmail(auth.currentUser!!.email.toString())
                            User.setUserLog(true)
                            Log.d("showInformed", "firbname: "+ User.getName())
                            Log.d("showInformed", "firbemail: " + auth.currentUser!!.email.toString())

                            finish()
                        }
                        // 아이디는 일치, 비밀번호가 불일치할 때
                        else{
                            Toast.makeText(applicationContext, "비밀번호 불일치", Toast.LENGTH_SHORT).show()
                        }
                        checking_id = false // 재로그인을 위해 로그인 offset을 처음 상태로 돌려 놓는다.
                    } else {
                        // 존재하지 않는 아이디
                        Toast.makeText(applicationContext, "존재하지 않는 아이디임, 재로그인", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // 데이터에 대한 접근이 취소되었을 때 동작하는 함수
                }
            })
            /* 2020-07-30(목) 이형석 수정
            if (str_pass.equals(user_password)) {
                Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                tMapView!!.invalidate() // 2020-07-30(목) 작성
            }
             */
        }
```

## * Facebook 로그인 버튼 클릭 시
```kotlin
private fun facebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult?) {
                //페이스북 로그인 성공
                handleFacebookAccessToken(result?.accessToken)
                Toast.makeText(applicationContext,"페이스북 로그인 성공",Toast.LENGTH_SHORT).show()
                var user:FirebaseUser? = auth.currentUser
                User.setName(user!!.displayName.toString())
                User.setEmail("FaceBook")
                User.setUserLog(true)
                User.setFBlogin(true)

                Log.d("showInformed", "name: "+ user.displayName.toString())
                Log.d("showInformed", "email" + auth.currentUser!!.email.toString())
                finish()
            }
            override fun onCancel() {
                //페이스북 로그인 취소
                //updateUI(null)
            }

            override fun onError(error: FacebookException?) {
                //페이스북 로그인 실패
                //updateUI(null)
            }
        })
    }


    private fun handleFacebookAccessToken(token: AccessToken?) {
        Log.d("MainActivity", "handleFacebookAccessToken:$token")
        if (token != null) {
            val credential = FacebookAuthProvider.getCredential(token.token)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("MainActivity", "signInWithCredential:success")
                        //updateUI(user)
                    } else {
                        Log.w("MainActivity", "signInWithCredential:failure", task.exception)
                        Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }
    }
```

## * 앱이 처음실행되면 Activity_login이 실행되고 만약 현재 로그인 된 상태라면 현재 로그인 된 사용자 정보를 User클래스에 데이터 저장
```kotlin
 override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        // Check if user is signed in (non-null) and update UI accordingly.
        Log.d(TAG, "firebaseAuthWithGoogle:" + currentUser?.displayName)
        if(currentUser !=null){
            Toast.makeText(this,"로그인 상태입니다.",Toast.LENGTH_SHORT).show()
            Log.d("@@@@", currentUser!!.email.toString())

            User.setName(currentUser.displayName.toString())
            User.setEmail(currentUser.email.toString())
            User.setUserLog(true)
            finish()
        }
    }
```

## * Google Login버튼 클릭 시 
```kotlin
   private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
```

## * 구글 로그인이 되는 과정
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
        else{
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext,"구글로그인 성공",Toast.LENGTH_SHORT).show()
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    var user:FirebaseUser? = auth.currentUser
                    User.setName(user!!.displayName.toString())
                    User.setEmail(user!!.email.toString())
                    User.setUserLog(true)

                    Log.d("showInformed", "name: "+ auth.currentUser!!.displayName.toString())
                    Log.d("showInformed", "email" + auth.currentUser!!.email.toString())
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
    // [END auth_with_google]
```

# 개선해야 할 점
#### - 로그인 후 QR코드 인식을 하여 실제로 전동 킥보드를 대여할 수 있는 기능을 구현하지 못함 (전동킥보드가 없었고 QR코드를 생성하지 못함)
#### - 로그인 후 getCurrentUser를 이용하여 Firebase에서 현재 로그인 된 사용자 정보를 불러오지 못함(구글로그인만 제대로 동작함)
#### - 현재위치가 갱신되는 주기가 너무 김
#### - 네비게이션 레이아웃의 로그인/회원가입 버튼의 동적 변경이 안됨
#### - 임의로 설정한 전동 킥보드의 마커가 사라지는 현상
#### - 자동차, 지하철, 도보, 자전거의 길찾기 기능은 존재하지만 전동 킥보드의 길찾기 기능이 없다는 점 등
