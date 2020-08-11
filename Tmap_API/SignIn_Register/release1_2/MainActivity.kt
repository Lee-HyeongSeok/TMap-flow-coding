package com.example.kick_going_tmap

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PointF
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_dialog.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), TMapGpsManager.onLocationChangedCallback {
    private lateinit var mAuth: FirebaseAuth // firebase 인증을 위한 객체
    private lateinit var auth:FirebaseAuth
    private var tmapView: TMapView? = null // 지도를 보여주기 위한 객체

    private var myLongitude: Double = 0.0 // 현재위치, 경도
    private var myLatitude: Double = 0.0   // 현재위치, 위도
    //var data = HashMap<String, TMapMarkerItem>() // 킥고잉 마커명, 위치 저장 해시맵
    var data = ArrayList<TMapMarkerItem>() // 킥고잉 위치를 담고있는 ArrayList
    var pDistance:makedDistanceEvent = makedDistanceEvent() // 거리를 계산하기 위한 클래스 객체
    var n=0 // 킥고잉 마커 번호를 지정하기 위한 변수

    // 2020-08-05(수) 작성
    // 구글 로그인
    private lateinit var googleSignInClient: GoogleSignInClient
    private var Login:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 사용자 인증 부분 : 앱 관리자
        // 2020-07-28일 화요일 작성
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword("email", "password") // 하드 코딩 처리 나중에
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                }
            }

        // 2020-08-05(수) 작성
        // 구글 로그인을 위함
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        // 2020-07-28일 화요일 작성
        // tmap 표시할 레이아웃 선언
        val linear: RelativeLayout = findViewById(R.id.mapLayout) as RelativeLayout

        // 2020-07-28일 화요일 작성
        // 2020-07-29일 수요일 수정
        tmapView = TMapView(this)
        tmapView?.setSKTMapApiKey("your app key") // 하드 코딩 처리 나중에
        linear.addView(tmapView)
        tmapView!!.setIconVisibility(true)
        tmapView!!.setCompassMode(true)
        tmapView!!.setSightVisible(true)
        setGps()

        // 2020-07-28일 화요일 작성
        // 대여하기 버튼 누를 시 동작
            Rental_Btn.setOnClickListener {
                if(Login == false){
                    var log_dialog: LinearLayout =
                        View.inflate(this@MainActivity, R.layout.login_dialog, null) as LinearLayout
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                    val dialogIDText = log_dialog.findViewById<EditText>(R.id.edit_id)
                    val dialogPASSText = log_dialog.findViewById<EditText>(R.id.edit_pass)
                    val dialogRegi = log_dialog.findViewById<TextView>(R.id.Register_Btn)
                    val googleLogBtn = log_dialog.findViewById<TextView>(R.id.google_Btn)
                    val intent: Intent = Intent(this, RegisterEvent::class.java)
                    var checking_id: Boolean = false

                    builder.setView(log_dialog)
                        .setPositiveButton("확인") { dialogInterface, i ->
                            val str_id: String = dialogIDText.text.toString()
                            val str_pass: String = dialogPASSText.text.toString()

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
                                            Login = true // 2020-08-11 작성, 로그인 성공 시 대여하기 버튼 동작 상이하게

                                            // 로그인 시 주변 킥고잉 위치를 보여줌
                                            // 2020-08-06(목) 수정
                                            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                                            val kickGoingRef: DatabaseReference = database.getReference("marker_list") // DB에 marker_list에 접근

                                            kickGoingRef.addValueEventListener(object: ValueEventListener{
                                                override fun onCancelled(error: DatabaseError) {
                                                }
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    for(kickgoingSnapshot in snapshot.children){
                                                        var lat = kickgoingSnapshot.child("latitude").getValue<Double>()
                                                        var lon = kickgoingSnapshot.child("longitude").getValue<Double>()
                                                        var model = kickgoingSnapshot.child("model").getValue<String>()

                                                        var kickgoingPoint: TMapPoint = TMapPoint(lat!!, lon!!)
                                                        var marker:TMapMarkerItem = TMapMarkerItem() // 마커를 위한 Item List 객체
                                                        //var marker: TMapMarkerItem = TMapMarkerItem()
                                                        marker.tMapPoint = kickgoingPoint
                                                        marker.visible = TMapMarkerItem.VISIBLE
                                                        marker.canShowCallout = true
                                                        marker.calloutTitle=model
                                                        data.add(marker) // HashMap에 데이터 삽입

                                                        // 2020-07-31(금) 수정
                                                        // 현재위치와 각 킥고잉들의 거리 계산
                                                        // CalcDistance = makedDistanceEvent.kt 클래스의 메소드
                                                        var dist = pDistance.CalcDistance(marker.tMapPoint.latitude, marker.tMapPoint.longitude, myLatitude, myLongitude)
                                                        marker.calloutSubTitle = "${dist}m"
                                                        tmapView!!.addMarkerItem("marker${n}", marker)
                                                        n++
                                                    }
                                                    tmapView!!.setCompassMode(true) // 2020-07-30(목) 작성
                                                    tmapView!!.invalidate() // 2020-07-30(목) 작성
                                                }

                                            })
                                        }
                                        // 2020-07-30(목) 작성
                                        // 비밀번호 예외처리 추가
                                        else{
                                            Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                                        }
                                        checking_id = false // 재로그인을 위해 로그인 offset을 처음 상태로 돌려 놓는다.
                                    } else {
                                        // 존재하지 않는 아이디
                                        Toast.makeText(applicationContext, "존재하지 않는 아이디임, 재로그인", Toast.LENGTH_SHORT).show()
                                        tmapView!!.invalidate() // 2020-07-30(목) 작성
                                    }
                                }
                                override fun onCancelled(error: DatabaseError) {
                                    // 데이터에 대한 접근이 취소되었을 때 동작하는 함수
                                }
                            })
                        }
                        .setNegativeButton("취소") { dialogInterface, i ->
                            dialogInterface.dismiss()
                            tmapView!!.invalidate() // 2020-07-30(목) 작성
                        }.show()
                    // 다이얼로그 상자에 회원가입 버튼 누를 시 회원가입 페이지로 인텐트 전환
                    dialogRegi.setOnClickListener {
                            startActivity(intent)
                    }
                    // 2020-08-05(수) 작성
                    // 구글 로그인 버튼
                    googleLogBtn.setOnClickListener {
                        signIn()
                        Login = true // 2020-08-11(화) 작성, 구글 로그인 이후
                    }
                } // end of if(Login == false)
                else if(Login == true){
                    Rental_Btn.setOnClickListener{
                        var intentIntegrator:IntentIntegrator = IntentIntegrator(this)
                        intentIntegrator.initiateScan()
                    }
                }
            } // end of Rental_Btn Listener



        // 2020-07-30(목) 작성
        tmapView!!.setOnClickListenerCallBack(object: TMapView.OnClickListenerCallback{
            override fun onPressEvent(
                p0: ArrayList<TMapMarkerItem>?,
                p1: ArrayList<TMapPOIItem>?,
                p2: TMapPoint?,
                p3: PointF?
            ): Boolean {
                // 2020-07-31(금) 작성
                // 누른 마커와 현재위치 사이 거리를 서브 타이틀로 세팅
                p0!!.forEach { i ->
                    Toast.makeText(applicationContext, "${i.calloutTitle}", Toast.LENGTH_SHORT).show()
                    var dist = pDistance.CalcDistance(i.latitude, i.longitude, myLatitude, myLongitude)
                    i.calloutSubTitle = "${dist}m"
                }
                return false
            }
            override fun onPressUpEvent(
                p0: ArrayList<TMapMarkerItem>?,
                p1: ArrayList<TMapPOIItem>?,
                p2: TMapPoint?,
                p3: PointF?
            ): Boolean {
                //tmapView!!.setTrackingMode(true)
                tmapView!!.setCompassMode(true)
                tmapView!!.setSightVisible(true)
                tmapView!!.invalidate()
                return false
            }
        }) // end of setOnClickListenerCallBack()

    } // end of onCreate() method

    // 2020-07-29 작성
    // 현재위치 받아오는 리스너
    private val mLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                myLongitude = location.longitude
                myLatitude = location.latitude

                tmapView!!.setLocationPoint(myLongitude, myLatitude)
                tmapView!!.setCenterPoint(myLongitude, myLatitude)
                tmapView!!.setTrackingMode(true)

            }
        } // end of onLocationChanged() method
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }
    } // end of Listener

    // 2020-07-29 작성
    // 현재위치를 위한 gps를 받아오는 함수
    fun setGps() {
        val lm =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }
        lm.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,  // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
            1000, 1f,  // 통지사이의 최소 변경거리 (m)
            mLocationListener
        )
    }// end of setGps()

    // 2020-07-31 금
    // 현재위치가 변경될 때마다 킥고잉과 현재위치 사이 거리를 킥고잉 마커 서브 타이틀에 세팅해주는 메소드
    override fun onLocationChange(p0: Location?) {

        for(i in data){ // data에는 DB에서 받아온 킥고잉 마커들이 담겨있음
            var lat = i.tMapPoint.latitude
            var lon = i.tMapPoint.longitude
            var model = i.calloutTitle

            var p:TMapPoint = TMapPoint(lat, lon)
            var mark:TMapMarkerItem = TMapMarkerItem()
            mark.tMapPoint = p
            mark.visible = TMapMarkerItem.VISIBLE
            mark.canShowCallout = true
            mark.calloutTitle=model
            var dist = pDistance.CalcDistance(lat, lon, p0!!.latitude, p0!!.longitude)
            mark.calloutSubTitle = "${dist}m"
            //tmapView!!.addMarkerItem("marker${n}", mark)
        }
    } // end of onLocationChange method

    // 2020-08-05(수) 작성
    // 구글 로그인
    private fun signIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle: "+ account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e: ApiException){
                Log.w(TAG, "Google sign in failed", e)
            }
        }

        var result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){
            if(result.contents == null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Scanned" + result.contents, Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext, "google login success", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "signInWithCredential: success")
                }
                else{
                    Log.w(TAG, "signInWithCredential: failure", task.exception)
                }
            }
    }


    companion object{
        private const val TAG="GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

} // end of MainActivity class
