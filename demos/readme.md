## 카카오맵 API를 사용한 샘플 코드   
#### 출처 : https://apis.map.kakao.com/android/   

#### 구성    
		* CameraDemoActivity.java   
		* EventsDemoActivity.java   
		* MapViewDemoActivity.java   
		* MarkerDemoActivity.java   
		* LocationDemoActivity.java   
		* PolygonDemoActivity.java   

#### * 주요 코드   setMapViewEventListener   
		* MapViewEventListener interface를 구현하는 객체를 MapView에 등록하여 지도 이동, 확대, 축소 및 지도 화면 터치(Single Tap, Double Tap, Long Press) 이벤트를 통보받을 수 있다.

#### CameraDemoActivity.java   
			* MapView 초기화가 끝나고 지도 이미지 로딩을 할 준비가 되었을 때 호출된다.

```java

// 위도(latitude), 경도(longitude) 순 
private static final MapPoint MAP_POINT_POI1 = MapPoint.mapPointWithGeoCoord(37.537229, 127.005515);

// 중략 

@Override
    public void onMapViewInitialized(MapView mapView) {
        // MapView had loaded. Now, MapView APIs could be called safely.

        MapPOIItem poiItem1 = new MapPOIItem();	// 마커를 설정하기 위한 객체 선언
        poiItem1.setItemName("POI1");	// 마커 이름 설정 
        poiItem1.setMapPoint(MAP_POINT_POI1); // 마커의 좌표 설정 
        poiItem1.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커의 기본 보여지는 타입 설정 
        mapView.addPOIItem(poiItem1); // 마커 등록 

        MapPOIItem poiItem2 = new MapPOIItem();
        poiItem2.setItemName("POI2");
        poiItem2.setMapPoint(MAP_POINT_POI2);
        poiItem2.setMarkerType(MapPOIItem.MarkerType.YellowPin);
        mapView.addPOIItem(poiItem2);
    }
```   

#### EventsDemoActivity.java   

```java

/*
mapView.setMapViewEventListener(this); 호출로 인해
지도 화면 터치(Single Tap, Double Tap, Long Press) 이벤트를 통보받을 수 있다.
*/
@Override
	public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
		MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord(); // 현재 맵의 위치를 받아온다.

		// getMapPointScreenLocation() 메소드는 MapPoint.PlainCoordinate를 반환한다.
		// MapPoint 객체가 나타내는 지점의 좌표값을 MapView 좌상단 기준 pixel 좌표값으로 조회한다.
		MapPoint.PlainCoordinate mapPointScreenLocation = mapPoint.getMapPointScreenLocation();
		mTapTextView.setText("single tapped, point=" + String.format("lat/lng: (%f,%f) x/y: (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude, mapPointScreenLocation.x, mapPointScreenLocation.y));
		Log.i(LOG_TAG, String.format("MapView onMapViewSingleTapped (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
	}

	@Override
	public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
		MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

		// getMapPointScreenLocation() 메소드는 MapPoint.PlainCoordinate를 반환한다.
		// MapPoint 객체가 나타내는 지점의 좌표값을 MapView 좌상단 기준 pixel 좌표값으로 조회한다.
		MapPoint.PlainCoordinate mapPointScreenLocation = mapPoint.getMapPointScreenLocation();
		mTapTextView.setText("double tapped, point=" + String.format("lat/lng: (%f,%f) x/y: (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude, mapPointScreenLocation.x, mapPointScreenLocation.y));
		Log.i(LOG_TAG, String.format(String.format("MapView onMapViewDoubleTapped (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude)));
	}

	@Override
	public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
		MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

		// getMapPointScreenLocation() 메소드는 MapPoint.PlainCoordinate를 반환한다.
		// MapPoint 객체가 나타내는 지점의 좌표값을 MapView 좌상단 기준 pixel 좌표값으로 조회한다.
		MapPoint.PlainCoordinate mapPointScreenLocation = mapPoint.getMapPointScreenLocation();
		mTapTextView.setText("long pressed, point=" + String.format("lat/lng: (%f,%f) x/y: (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude, mapPointScreenLocation.x, mapPointScreenLocation.y));
		Log.i(LOG_TAG, String.format(String.format("MapView onMapViewLongPressed (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude)));
	}
```