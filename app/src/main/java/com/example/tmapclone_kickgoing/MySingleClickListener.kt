package org.techtown.mapservicewithtmapapi

import android.graphics.PointF
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

class MySingleClickListener(findPath: FloatingActionButton) :
    TMapView.OnClickListenerCallback {
    var findPathinL = findPath

    override fun onPressEvent(
        p0: java.util.ArrayList<TMapMarkerItem>?,
        p1: java.util.ArrayList<TMapPOIItem>?,
        p2: TMapPoint?,
        p3: PointF?
    ): Boolean {
        return true
    }

    override fun onPressUpEvent(
        p0: java.util.ArrayList<TMapMarkerItem>?,
        p1: java.util.ArrayList<TMapPOIItem>?,
        p2: TMapPoint?,
        p3: PointF?
    ): Boolean {

        return true
    }
}