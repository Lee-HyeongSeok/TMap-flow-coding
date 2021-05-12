package org.techtown.mapservicewithtmapapi

import android.os.AsyncTask
import android.util.Log
import android.widget.RelativeLayout
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapPolyLine
import com.skt.Tmap.TMapView

class GetWeatherTask(TmapLayout: RelativeLayout,tmap: TMapView, pointARR: ArrayList<TMapPoint>, tPoly: TMapPolyLine) : AsyncTask<Unit, Unit, Boolean>() {

    var innerTmap = tmap
    var innerSp = pointARR[0]
    var innerEp = pointARR[1]
    var tMapPolyLine = tPoly
    var map = TmapLayout

    override fun doInBackground(vararg params: Unit?): Boolean {
        tMapPolyLine = TMapData().findPathData(innerSp, innerEp)

        Log.d("MainActivity", "길찾기 성공")
        return true
    }


    override fun onPostExecute(result: Boolean?) {

        innerTmap.addTMapPolyLine("Line1", tMapPolyLine)
        map.invalidate()
        Log.d("MainActivity distance t", tMapPolyLine.distance.toString())

        return
    }
}