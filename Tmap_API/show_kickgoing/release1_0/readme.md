## * Firebase Realtime DB를 활용한 전동킥보드 표시 서비스   
#### * 출처 : https://tmapapi.sktelecom.com/index.html   
#### * 코드 출처 : https://github.com/leehyeongseck/TMap-flow-coding/tree/dev/Tmap_API/SignIn_Register/release1_0   
#### * 1차 릴리즈 날짜 : 2020-07-30(목)   


* 기능 요구사항   
		- 사용자는 처음 앱 실행 시 현재위치에 해당하는 지역의 공유 전동킥보드들을 확인할 수 있어야 한다.   
		- 사용자는 공유 전동킥보드들과의 현재 위치 사이 거리를 확인할 수 있어야 한다.   
		- 공유 전동킥보드들의 위도, 경도, 모델명 등의 정보는 Firebase Realtime DB에 저장되어야 한다.   
		- 공유 전동킥보드들은 빨간색 마커로 표시된다.   
		- 마커를 클릭 후 캔슬했을 때 사용자는 여전히 자신의 현재위치를 유지할 수 있어야 한다.   
		- 마커를 클릭 후 캔슬했을 때 사용자는 자신이 바라보는 방향을 유지할 수 있어야 한다.   

#### MainActivity.kt   
		- DB에서 킥고잉 정보를 가져온다.   
		- for문으로 순회하면서 자식노드에 접근   


```kotlin
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
                tmapView!!.setCompassMode(true) // 단말기가 바라보는 방향 표시 
                tmapView!!.invalidate() // 지도 갱신
            }

        })
```   

```kotlin
// 2020-07-30(목) 작성
// 사용자가 마커 클릭 후 캔슬 시 지도 갱신 
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
```   


#### makedDistanceEvent.kt   
		- 현재위치와 마커간의 거리를 계산하는 클래스   


```kotlin
class makedDistanceEvent {
    class DistanceEvent(){} // 생성자
 
    // CalcDistance(현재 위도, 현재 경도, 마커 위도, 마커 경도) 
    public fun CalcDistance(lat1:Double, lon1:Double, lat2:Double, lon2:Double): Int{

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
```