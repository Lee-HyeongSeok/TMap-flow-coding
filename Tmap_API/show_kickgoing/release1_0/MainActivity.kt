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
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var tmapView: TMapView? = null

    private var myLongitude: Double = 0.0 // 현재위치, 경도
    private var myLatitude: Double = 0.0   // 현재위치, 위도
    var data = HashMap<String, TMapMarkerItem>() // 킥고잉 마커명, 위치 저장 해시맵
    var pDistance:makedDistanceEvent = makedDistanceEvent()
    var n=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 사용자 인증 부분 : 앱 관리자
        // 2020-07-28일 화요일 작성
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword("kea7111@gmail.com", "jjhh@0515") // 하드 코딩 처리 나중에
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                }
            }

        // 2020-07-28일 화요일 작성
        // tmap 표시할 레이아웃 선언
        val linear: RelativeLayout = findViewById(R.id.mapLayout) as RelativeLayout

        // 2020-07-28일 화요일 작성
        // 2020-07-29일 수요일 수정
        //val tmapView:TMapView = TMapView(this)

        tmapView = TMapView(this)
        tmapView?.setSKTMapApiKey("l7xxca4265e638094d98b6baab6062b4d7a2") // 하드 코딩 처리 나중에
        linear.addView(tmapView)
        tmapView!!.setIconVisibility(true)
        tmapView!!.setCompassMode(true)
        tmapView!!.setSightVisible(true)
        setGps()

        // 2020-07-30(목) 작성
        // DB에 등록된 킥고잉 정보 가져오기
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
                    n++

                    var kickgoingPoint: TMapPoint = TMapPoint(lat!!, lon!!)
                    var marker: TMapMarkerItem = TMapMarkerItem()
                    marker.tMapPoint = kickgoingPoint
                    marker.visible = TMapMarkerItem.VISIBLE
                    marker.canShowCallout = true
                    marker.calloutTitle=model

                    data.put("marker${n}", marker) // HashMap에 데이터 삽입
                    // 현재위치와 각 킥고잉들의 거리 계산
                    // CalcDistance = makedDistanceEvent.kt 클래스의 메소드
                    var dist = pDistance.CalcDistance(myLatitude, myLongitude, marker.tMapPoint.latitude, marker.tMapPoint.longitude)
                    marker.calloutSubTitle = "${dist}m"
                    var s: String? = kickgoingSnapshot.key // 마커 이름 어케되는지 알아보려는 코드
                    tmapView!!.addMarkerItem("marker${n}", marker)
                }
                tmapView!!.setCompassMode(true)
                tmapView!!.invalidate() // 2020-07-30(목) 작성
            }

        })

        // 2020-07-28일 화요일 작성
        // 대여하기 버튼 누를 시 동작
        Rental_Btn.setOnClickListener {
            var log_dialog: LinearLayout =
                View.inflate(this@MainActivity, R.layout.login_dialog, null) as LinearLayout
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            val dialogIDText = log_dialog.findViewById<EditText>(R.id.edit_id)
            val dialogPASSText = log_dialog.findViewById<EditText>(R.id.edit_pass)
            val dialogRegi = log_dialog.findViewById<TextView>(R.id.Register_Btn)
            val intent: Intent = Intent(this, RegisterEvent::class.java)
            var user_password: String? = null
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
                    if (str_pass.equals(user_password)) {
                        Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                        tmapView!!.invalidate() // 2020-07-30(목) 작성
                    }
                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    dialogInterface.dismiss()
                    tmapView!!.invalidate() // 2020-07-30(목) 작성
                }.show()

            // 다이얼로그 상자에 회원가입 버튼 누를 시 회원가입 페이지로 인텐트 전환
            dialogRegi.setOnClickListener {
                startActivity(intent)
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
               // tmapView!!.setTrackingMode(true)
                tmapView!!.setCompassMode(true)
                tmapView!!.setSightVisible(true)
                tmapView!!.invalidate()
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

        })


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
        }

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

} // end of MainActivity class