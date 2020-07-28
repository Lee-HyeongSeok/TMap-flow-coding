package com.example.quickgoing

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_web_view_event.*
import kotlinx.android.synthetic.main.login_dialog.*
import net.daum.mf.map.api.*
import net.daum.mf.map.api.MapView.CurrentLocationTrackingMode
import java.lang.Double.valueOf
import java.util.*

class MainActivity : AppCompatActivity(), MapView.CurrentLocationEventListener,
MapReverseGeoCoder.ReverseGeoCodingResultListener, MapView.MapViewEventListener{

    lateinit var map:MapView
    var mapPoiItem: MapPOIItem = MapPOIItem()

    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100

    private var longitude: Double = 0.0 // 경도
    private var latitude: Double = 0.0 // 위도
    private lateinit var provider:String
    private lateinit var currentLocation: String

    var REQUIRED_PERMISSIONS = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        map =  MapView(this)

        // 현위치 트래킹 이벤트를 통보받을 수 있다.
        map.setCurrentLocationEventListener(this)
        map.setMapViewEventListener(this)
        map_view.addView(map)
        val intent:Intent = Intent(this, WebViewEvent::class.java)

        // 버튼 누를 시 현위치로 카메라 이동
        CurrentLocationTracking.setOnClickListener {
           map.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading)
            map.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true)
        }

        webViewBtn.setOnClickListener {
            startActivity(intent)
        }
        // 킥고잉 대여하기 버튼 누를 시 로그인/회원가입 창이 뜸
        Rental_Service.setOnClickListener {
            var linear:LinearLayout = View.inflate(this@MainActivity, R.layout.login_dialog, null) as LinearLayout
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            val dialogIDText = linear.findViewById<EditText>(R.id.edit_id) // 아이디 입력받는 에디터
            val dialogPASSText = linear.findViewById<EditText>(R.id.edit_pass) // 비밀번호 입력받는 에디터
            val dialogRegi = linear.findViewById<TextView>(R.id.Register_Btn) // 회원가입 버튼
            val intent:Intent = Intent(this, RegisterEvent::class.java) // 회원가입 페이지 인텐트

            builder.setView(linear)
                .setPositiveButton("확인"){dialogInterface, i ->
                    val str_id:String = dialogIDText.text.toString()
                    val str_pass:String = dialogPASSText.text.toString()
                    // DB에 접근해서 로그인한 아이디와 비밀번호 일치성 검사, 운전면허 여부 검사
                    Toast.makeText(this, "${str_id}  /  ${str_pass}", Toast.LENGTH_SHORT).show()

                }
                .setNegativeButton("취소"){dialogInterface, i ->
                    dialogInterface.dismiss()
                }.show()

            dialogRegi.setOnClickListener {
                startActivity(intent)
            }
        }

    }

    override fun onResume() {
        super.onResume()

        if(!checkLocationServicesStatus()){
            showDialogForLocationServiceSetting()
        }else{
            checkRunTimePermission()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        map.setCurrentLocationTrackingMode(CurrentLocationTrackingMode.TrackingModeOff)
        map.setShowCurrentLocationMarker(false)
    }

    override fun onMapViewDoubleTapped(mapView: MapView, mapPoint:MapPoint){

    }

    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint) {
        var mapPointGeo:MapPoint.GeoCoordinate = p1.getMapPointGeoCoord()

        var alertDialog:AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("double tapped")
        alertDialog.setMessage(String.format("Double-Tap on (%f, %f", mapPointGeo.latitude, mapPointGeo.longitude))
        alertDialog.setPositiveButton("OK", null)
        alertDialog.show()
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint) {
        var mapPointGeo:MapPoint.GeoCoordinate = p1.getMapPointGeoCoord()

        var alertDialog:AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("double tapped")
        alertDialog.setMessage(String.format("Double-Tap on (%f, %f", mapPointGeo.latitude, mapPointGeo.longitude))
        alertDialog.setPositiveButton("OK", null)
        alertDialog.show()
    }

    private fun checkLocationServicesStatus():Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    // 단말의 현위치 좌표값을 통보받을 수 있다.

    override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float){
        val mapPointGeo: MapPoint.GeoCoordinate = p1!!.getMapPointGeoCoord()
        longitude = mapPointGeo.longitude
        latitude = mapPointGeo.latitude
        // mapPointGeo.latitude // 위도
        // mapPointGeo.longitude // 경도
    }
    // 단말의 방향 각도값을 통보받을 수 있다.
    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {

    }

    // 현위치 갱신 작업에 실패한 경우 호출됨
    override fun onCurrentLocationUpdateFailed(p0: MapView?) {

    }

    // 현위치 트랙킹 기능이 사용자에 의해 취소된 경우 호출된다.
    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {

    }
    override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {
        onFinishReverseGeoCoding("Fail")
    }

    override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, p1: String?) {
        //MapReverseGeoCoder.toString()
        onFinishReverseGeoCoding(p1!!)
    }

    private fun onFinishReverseGeoCoding(result: String) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == REQUIRED_PERMISSIONS.size) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            var check_result = true


            // 모든 퍼미션을 허용했는지 체크합니다.
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {
                Log.d("@@@", "start")
                //위치 값을 가져올 수 있음
                map.setCurrentLocationTrackingMode(CurrentLocationTrackingMode.TrackingModeOnWithHeading)
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        REQUIRED_PERMISSIONS[0]
                    )
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun checkRunTimePermission() {
        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this@MainActivity,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음
            map.setCurrentLocationTrackingMode(CurrentLocationTrackingMode.TrackingModeOnWithHeading)
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    REQUIRED_PERMISSIONS.get(0)
                )
            ) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this@MainActivity, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG)
                    .show()
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this@MainActivity, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this@MainActivity, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }

    }

    private fun showDialogForLocationServiceSetting() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            """앱을 사용하기 위해서는 위치 서비스가 필요합니다.
                위치 설정을 수정하실래요?
                """.trimIndent()
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialog, id ->
            val callGPSSettingIntent =
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
        }).setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
        builder.create().show()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음")
                        checkRunTimePermission()
                        return
                    }
                }
        }
    }


    private fun CalcDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int{
        var EARTH_R: Double = 6371000.0
        var Rad: Double = Math.PI/180
        var radLat1: Double = Rad*lat1
        var radLat2: Double = Rad*lat2
        var radDist: Double = Rad*(lon1 - lon2)
        var distance:Double = Math.sin(radLat1) * Math.sin(radLat2)
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist)
        var ret:Double = EARTH_R * Math.acos(distance)
        var rslt:Long = Math.round(Math.round(ret).toDouble() / 1000);
        var result = Math.round(ret).toInt() /* M 계산 */
        return result;
    }


}


