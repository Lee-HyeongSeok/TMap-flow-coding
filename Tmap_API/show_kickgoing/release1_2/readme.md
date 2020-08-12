## Firebase Realtime DB를 활용한 전동킥보드 표시 서비스(릴리즈 1.2 버전)   
#### 프로젝트 출처 : 한국산업기술대학교 소프트웨어 공학과, 컴퓨터 공학과   
#### API 출처 : https://tmapapi.sktelecom.com/   

* 3차 릴리즈 날짜 : 2020-08-12(수)   
* 3차 릴리즈 내용   
		1. 클릭된 마커와 자신의 거리를 갱신하여 해당 마커의 서브 타이틀에 표시해준다.   
		2. makedDistance 클래스 삭제   
		3. 마커와 자신의 거리를 TmapPolyLine의 메소드인 distance를 사용하여 toInt()로 형변환   
		4. 위치 갱신 시간을 1000 -> 800으로, 위치 갱신 거리를 1f -> 0.5f로 변경   
		5. UI 스레드를 사용하여 마커와의 거리를 갱신   

#### 5. UI 스레드 사용하는 부분   

```kotlin

// 코틀린 

 private val mLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                myLongitude = location.longitude
                myLatitude = location.latitude

                tmapView!!.setLocationPoint(myLongitude, myLatitude)
                tmapView!!.setCenterPoint(myLongitude, myLatitude)
                tmapView!!.setTrackingMode(true)

                // 2020-08-12(수) 작성
                // UI 스레드를 통해서 눌린 마커의 서브 타이틀(거리)을 지속적으로 갱신시킨다.
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
                            var tpolyLine = TMapPolyLine() // polyLine 생성 
                            tpolyLine.addLinePoint(p) // 마커 포인트 추가
                            tpolyLine.addLinePoint(myLocation) // 현재위치 포인트 추가
                            var dist = tpolyLine.distance.toInt()// 3. 마커와 자신의 위치 거리를 구하는 메소드 사용
                            i.calloutSubTitle = "${dist}m" // 서브 타이틀에 구해진 거리를 갱신 
                            i.autoCalloutVisible = true
                            tmapView!!.invalidate() // 맵 갱신 
                        }
                    }
                }).start()


            }
        } // end of onLocationChanged() method
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }
    } // end of Listener
```   

#### 4. 위치 갱신 기준 변경   

```kotlin
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
            800, 0.5f,  // 통지사이의 최소 변경거리 (m), 1000 -> 800 / 1f -> 0.5f
            mLocationListener
        )
    }// end of setGps()
```
