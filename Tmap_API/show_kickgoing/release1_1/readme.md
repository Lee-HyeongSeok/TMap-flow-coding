## Firebase Realtime DB를 활용한 전동킥보드 표시 서비스(릴리즈 1.1 버전)   
#### 프로젝트 출처 : 한국산업기술대학교 소프트웨어공학과, 컴퓨터공학과   
#### API 출처 : https://tmapapi.sktelecom.com/   


#### * 2차 릴리즈 날짜 : 2020-07-31(금)   
#### * 2차 릴리즈 내용   
		* makedDistanceEvent 클래스의 CalcDistance() 메소드 사용 개선   
				* CalcDistance(목적지 위도, 목적지 경도, 현재위치 위도, 현재위치 경도)로 수정   
				* 마커의 서브 타이틀에 표시되는 값이 정확해짐   
		* MainActivity에 TMapGpsManager.onLocationChangedCallback 리스너를 implement   
				* 현재위치 변경 시 반응하는 메소드 추가   
				* onLocationChange() 오버라이드   
		* 테스트 목적으로 마커 클릭 시 해당 마커 타이틀 Toast   
				* tmapView.setOnClickListenerCallBack() 리스너의 onPressEvent 메소드를 오버라이드   
				* forEach문을 통해 눌린 마커 조사   
				* ArrayList로 Firebase DB의 킥보드 위치정보를 TMapMarkerItem 자료형으로 저장   
		* makedDistanceEvent.kt, RegisterEvent.kt 변경사항 없음   



#### Firebase DB의 킥보드 위치정보를 담는 전역변수 형태의 ArrayList   

```kotlin
var data = ArrayList<TMapMarkerItem>() // 킥고잉 위치를 담고있는 ArrayList
```   

#### 눌려진 마커를 조사하는 부분   

```kotlin
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
	       
                    // 눌린 마커와 현재위치의 거리를 계산하는 makedDistanceEvent 클래스의 메소드 
                    var dist = pDistance.CalcDistance(i.latitude, i.longitude, myLatitude, myLongitude)
                    i.calloutSubTitle = "${dist}"
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
```   

#### TMapGpsManager.onLocationChangedCallback 리스너 오버라이드 메소드 부분   


```kotlin
// 현재위치가 변경될 때마다 킥고잉과 현재위치 사이 거리를 킥고잉 마커 서브 타이틀에 세팅해주는 메소드
    override fun onLocationChange(p0: Location?) {
        Toast.makeText(applicationContext, "현재위치가 변경됨 ${p0!!.latitude} / ${p0!!.longitude}", Toast.LENGTH_SHORT).show()
        
        for(i in data){ // data에는 DB에서 받아온 킥고잉 마커들이 담겨있음(순회)
            var lat = i.tMapPoint.latitude // 각 마커들의 위도 순회
            var lon = i.tMapPoint.longitude // 각 마커들의 경도 순회 
            var model = i.calloutTitle // 각 마커들의 모델명 순회 

            // 각 마커들과 현재위치 사이 거리를 표시해 주기 위해 맵의 마커들을 초기화 하려는 목적 
            var p:TMapPoint = TMapPoint(lat, lon) // 각 마커들을 표시해주기 위한 위치 저장
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
```   

