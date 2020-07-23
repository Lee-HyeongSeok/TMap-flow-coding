package net.daum.android.map.openapi.sampleapp.demos;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.daum.android.map.openapi.sampleapp.MapApiConst;
import net.daum.android.map.openapi.sampleapp.R;
import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

public class MarkerDemoActivity extends FragmentActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener {

	private final int MENU_DEFAULT_CALLOUT_BALLOON = Menu.FIRST;
    private final int MENU_CUSTOM_CALLOUT_BALLOON = Menu.FIRST + 1;
    private final int MENU_SHOW_ALL = Menu.FIRST + 2;

    private static final MapPoint CUSTOM_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.537229, 127.005515);
    private static final MapPoint CUSTOM_MARKER_POINT2 = MapPoint.mapPointWithGeoCoord(37.447229, 127.015515);
    private static final MapPoint DEFAULT_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.4020737, 127.1086766);

    private MapView mMapView;
	private MapPOIItem mDefaultMarker;
    private MapPOIItem mCustomMarker;
    private MapPOIItem mCustomBmMarker;

    // CalloutBalloonAdapter 인터페이스 구현
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.ic_launcher);
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
            ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText("Custom CalloutBalloon");
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demo_nested_mapview);
        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setMapViewEventListener(this);
        mMapView.setPOIItemEventListener(this);

        // 구현한 CalloutBalloonAdapter 등록
        mMapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        createDefaultMarker(mMapView);
        createCustomMarker(mMapView);
        createCustomBitmapMarker(mMapView);
        showAll();
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_DEFAULT_CALLOUT_BALLOON, Menu.NONE, "Default CalloutBalloon");
        menu.add(0, MENU_CUSTOM_CALLOUT_BALLOON, Menu.NONE, "Custom CalloutBalloon");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	    	case MENU_DEFAULT_CALLOUT_BALLOON: {
                mMapView.removeAllPOIItems();
                mMapView.setCalloutBalloonAdapter(null);
                createDefaultMarker(mMapView);
                createCustomMarker(mMapView);
                showAll();
	    		return true;
	    	}
	    	case MENU_CUSTOM_CALLOUT_BALLOON: {
                mMapView.removeAllPOIItems();
                mMapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
                createDefaultMarker(mMapView);
                createCustomMarker(mMapView);
                showAll();
	    		return true;
	    	}

    	}
    	return super.onOptionsItemSelected(item);
    }

    private void createDefaultMarker(MapView mapView) {
        mDefaultMarker = new MapPOIItem();
        String name = "Default Marker";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(DEFAULT_MARKER_POINT);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(DEFAULT_MARKER_POINT, false);
    }

    private void createCustomMarker(MapView mapView) {
        mCustomMarker = new MapPOIItem();
        String name = "Custom Marker";
        mCustomMarker.setItemName(name);
        mCustomMarker.setTag(1);
        mCustomMarker.setMapPoint(CUSTOM_MARKER_POINT);

        mCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);

        mCustomMarker.setCustomImageResourceId(R.drawable.custom_marker_red);
        mCustomMarker.setCustomImageAutoscale(false);
        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(mCustomMarker);
    	mapView.selectPOIItem(mCustomMarker, true);
    	mapView.setMapCenterPoint(CUSTOM_MARKER_POINT, false);

    }

    private void createCustomBitmapMarker(MapView mapView) {
        mCustomBmMarker = new MapPOIItem();
        String name = "Custom Bitmap Marker";
        mCustomBmMarker.setItemName(name);
        mCustomBmMarker.setTag(2);
        mCustomBmMarker.setMapPoint(CUSTOM_MARKER_POINT2);

        mCustomBmMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.custom_marker_star);
        mCustomBmMarker.setCustomImageBitmap(bm);
        mCustomBmMarker.setCustomImageAutoscale(false);
        mCustomBmMarker.setCustomImageAnchor(0.5f, 0.5f);

        mapView.addPOIItem(mCustomBmMarker);
        mapView.selectPOIItem(mCustomBmMarker, true);
        mapView.setMapCenterPoint(CUSTOM_MARKER_POINT, false);
    }

    private void showAll() {
        int padding = 20;
        float minZoomLevel = 7;
        float maxZoomLevel = 10;
        MapPointBounds bounds = new MapPointBounds(CUSTOM_MARKER_POINT, DEFAULT_MARKER_POINT);
        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, padding, minZoomLevel, maxZoomLevel));
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        Toast.makeText(this, "Clicked " + mapPOIItem.getItemName() + " Callout Balloon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
