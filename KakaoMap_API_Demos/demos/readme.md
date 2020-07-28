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

#### LocationDemoActivitiy.java   

```java
// 현재 위치를 받아오면서 어플에 메뉴를 만드는 예제

 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demo_nested_mapview);

        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);

         // CurrentLocationEventListener interface를 구현하는 객체를 MapView 객체에 등록하여 현위치 트래킹 이벤트를 통보받을 수 있다.
        mMapView.setCurrentLocationEventListener(this);
    }

// 단말의 현위치 좌표값을 통보받을 수 있다.
// 단말의 현위치 자표값을 통보받아 onCreate의 mMapView.setCurrentLocationEventListener(this)로 통보한다.
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }
```   

#### MarkerDemoActivity.java   

```java 
 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demo_nested_mapview);
        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);

        // 이벤트를 통지받을 리스너 선언 
        mMapView.setMapViewEventListener(this);

        // POIItemEventListener interface를 구현하는 객체를 MapView에 등록하여 POI 관련 이벤트를 통보받을 수 있다.
        mMapView.setPOIItemEventListener(this); 

        // 구현한 CalloutBalloonAdapter 등록
        mMapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        createDefaultMarker(mMapView);
        createCustomMarker(mMapView);
        createCustomBitmapMarker(mMapView);
        showAll();
    }

// 마커 생성 메소드
private void createDefaultMarker(MapView mapView) {
        mDefaultMarker = new MapPOIItem(); // 마커 객체 생성 
        String name = "Default Marker"; // 마커 이름 
        mDefaultMarker.setItemName(name); // 마커 이름 세팅 
        mDefaultMarker.setTag(0); // 마커 식별 번호 세팅

        // 마커 위치 등록 
        mDefaultMarker.setMapPoint(DEFAULT_MARKER_POINT); // DEFAULT_MARKER_POINT는 위도, 경도 위치 정보 
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커 기본 타입 설정
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 눌렸을 때 마커 타입 설정 

        mapView.addPOIItem(mDefaultMarker); // MapView 객체에 마커 등록 
        mapView.selectPOIItem(mDefaultMarker, true); // MapView 객체에 마커가 선택되었을 때 등록 
        mapView.setMapCenterPoint(DEFAULT_MARKER_POINT, false); // 마커의 센터 위치 등록 
    }
```   

#### PolygonDemoActivity.java

```java

// 라인을 그릴 위도, 경도들의 배열 
mPolyline2Points = new MapPoint[]{
                MapPoint.mapPointWithWCONGCoord(475334.0, 1101210.0),
                MapPoint.mapPointWithWCONGCoord(474300.0,1104123.0),
                MapPoint.mapPointWithWCONGCoord(474300.0,1104123.0),
                MapPoint.mapPointWithWCONGCoord(473873.0,1105377.0),
                MapPoint.mapPointWithWCONGCoord(473302.0,1107097.0),
                MapPoint.mapPointWithWCONGCoord(473126.0,1109606.0),
                MapPoint.mapPointWithWCONGCoord(473063.0,1110548.0),
                MapPoint.mapPointWithWCONGCoord(473435.0,1111020.0),
                MapPoint.mapPointWithWCONGCoord(474068.0,1111714.0),
                MapPoint.mapPointWithWCONGCoord(475475.0,1112765.0),
                MapPoint.mapPointWithWCONGCoord(476938.0,1113532.0),
                MapPoint.mapPointWithWCONGCoord(478725.0,1114391.0),
                MapPoint.mapPointWithWCONGCoord(479453.0,1114785.0),
                MapPoint.mapPointWithWCONGCoord(480145.0,1115145.0),
                MapPoint.mapPointWithWCONGCoord(481280.0,1115237.0),
                MapPoint.mapPointWithWCONGCoord(481777.0,1115164.0),
                MapPoint.mapPointWithWCONGCoord(482322.0,1115923.0),
                MapPoint.mapPointWithWCONGCoord(482832.0,1116322.0),
                MapPoint.mapPointWithWCONGCoord(483384.0,1116754.0),
                MapPoint.mapPointWithWCONGCoord(484401.0,1117547.0),
                MapPoint.mapPointWithWCONGCoord(484893.0,1117930.0),
                MapPoint.mapPointWithWCONGCoord(485016.0,1118034.0)
        };

// 원을 그리는 메소드 
private void addCircles() {
	// 37.537094(위도), 127.005470(경도) 기준으로 500m 반경의 원을 그린다.
        MapCircle circle1 = new MapCircle(
        		MapPoint.mapPointWithGeoCoord(37.537094, 127.005470), // center
        		500, // radius
        		Color.argb(128, 255, 0, 0), // strokeColor(선)
        		Color.argb(128, 0, 255, 0) // fillColor(채우기)
        );
        circle1.setTag(1234);
        mMapView.addCircle(circle1);
        MapCircle circle2 = new MapCircle(
        		MapPoint.mapPointWithGeoCoord(37.551094, 127.019470), // center
        		1000, // radius
        		Color.argb(128, 255, 0, 0), // strokeColor 
        		Color.argb(128, 255, 255, 0) // fillColor
        );
        circle2.setTag(5678);
        mMapView.addCircle(circle2);
        
     // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
        MapPointBounds[] mapPointBoundsArray = { circle1.getBound(), circle2.getBound() };
        MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
        int padding = 50; // px

	// cameraUpdate에 정의된 명령어에 따라 카메라를 재배치한다.
	// cameraUpdate에 정의된 명령어 : CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding)
	// CameraUpdateFactory.newMapPointBounds() : 지도 화면을 현재의 확대/축소 레벨을 유지한 채로 설정한 중심점으로 이동
        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }

private void addPolyline2() {

	// findPOIItemByTag() : 지도화면에 추가된 POI Item들 중 tag 값이 일치하는 POI item을 찾는다.
        MapPOIItem existingPOIItemStart = mMapView.findPOIItemByTag(10001);
        if (existingPOIItemStart != null) {
            mMapView.removePOIItem(existingPOIItemStart);
        }

        MapPOIItem existingPOIItemEnd = mMapView.findPOIItemByTag(10002);
        if (existingPOIItemEnd != null) {
            mMapView.removePOIItem(existingPOIItemEnd);
        }

        MapPolyline existingPolyline = mMapView.findPolylineByTag(2000);
        if (existingPolyline != null) {
            mMapView.removePolyline(existingPolyline);
        }

        MapPOIItem poiItemStart = new MapPOIItem();
        poiItemStart.setItemName("Start");
        poiItemStart.setTag(10001);
        poiItemStart.setMapPoint(MapPoint.mapPointWithWCONGCoord(475334.0,1101210.0));
        poiItemStart.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItemStart.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
        poiItemStart.setShowCalloutBalloonOnTouch(false);
        poiItemStart.setCustomImageResourceId(R.drawable.custom_poi_marker_start);
        poiItemStart.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(29, 2));
        mMapView.addPOIItem(poiItemStart);

        MapPOIItem poiItemEnd = new MapPOIItem();
        poiItemEnd.setItemName("End");
        poiItemEnd.setTag(10001);
        poiItemEnd.setMapPoint(MapPoint.mapPointWithWCONGCoord(485016.0,1118034.0));
        poiItemEnd.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItemEnd.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
        poiItemEnd.setShowCalloutBalloonOnTouch(false);
        poiItemEnd.setCustomImageResourceId(R.drawable.custom_poi_marker_end);
        poiItemEnd.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(29, 2));
        mMapView.addPOIItem(poiItemEnd);

        MapPolyline polyline2 = new MapPolyline(21);
        polyline2.setTag(2000);
        polyline2.setLineColor(Color.argb(128, 0, 0, 255));
        polyline2.addPoints(mPolyline2Points);
        mMapView.addPolyline(polyline2);

        MapPointBounds mapPointBounds = new MapPointBounds(mPolyline2Points);
        int padding = 200; // px

	// moveCamera(CameraUpdate cameraUpdate) : cameraUpdate에 정의된 명령어에 따라 카메라를 재배치한다.
	// cameraUpdate에 정의된 명령어 : CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding)
	// CameraUpdateFactory.newMapPointBounds() : 지도 화면을 현재의 확대/축소 레벨을 유지한 채로 설정한 중심점으로 이동
        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }

private void addPolyline1() {

	// 1000번에 해당하는 마커를 찾는다.
        MapPolyline existingPolyline = mMapView.findPolylineByTag(1000);
        if (existingPolyline != null) {
            mMapView.removePolyline(existingPolyline);
        }

        MapPolyline polyline1 = new MapPolyline();
        polyline1.setTag(1000);
        polyline1.setLineColor(Color.argb(128, 255, 51, 0));
        polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.537229, 127.005515));
        polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.545024,127.03923));
        polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.527896,127.036245));
        polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.541889,127.095388));
        mMapView.addPolyline(polyline1);

	// MapPointBounds : 지정한 영역이 화면에 나타나도록 지도화면 중심과 확대/축소 레벨을 자동조절
	// newMapPointBounds(MapPointBounds mapPointBounds)
	// mapPointBounds : 화면에 보여주고자 하는 영역 
        MapPointBounds mapPointBounds = new MapPointBounds(polyline1.getMapPoints());
        int padding = 100; // px
        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }
```