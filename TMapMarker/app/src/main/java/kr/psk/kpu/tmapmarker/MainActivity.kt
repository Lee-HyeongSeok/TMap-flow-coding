package kr.psk.kpu.tmapmarker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var tMapView:TMapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tMapView= TMapView(this)
        tMapView.setSKTMapApiKey("l7xx8b3f878ffa1d4920b9be6d6214c85aed")
        map_view.addView(tMapView)


        setGps()
        tMapView.setIconVisibility(true)
        tMapView.setCompassMode(true)
        tMapView.setTrackingMode(true)
        tMapView.setSightVisible(true)



        val database = Firebase.database
        val myRef = database.getReference("marker_list")

        var n=0

        search.setOnClickListener {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (postSnapshot in dataSnapshot.children) {
                        var latitude=postSnapshot.child("latitude").getValue<Double>()
                        var longitude=postSnapshot.child("longitude").getValue<Double>()
                        var model=postSnapshot.child("model").getValue<String>()
                        Log.d("킥고잉","latitude : $latitude, longitude=$longitude, model : $model")
                        n++

                        var tpoint: TMapPoint = TMapPoint(latitude!!,longitude!!)
                        var marker: TMapMarkerItem = TMapMarkerItem()
                        marker.tMapPoint=tpoint
                        marker.visible=TMapMarkerItem.VISIBLE
                        marker.canShowCallout=true
                        marker.calloutTitle=model!!

                        tMapView.addMarkerItem("marker${n}",marker)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("myRef", "Failed to read value.", error.toException())
                }
            })
        }
    }

    private val mLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                val latitude: Double = location.latitude
                val longitude: Double = location.longitude
                tMapView.setLocationPoint(longitude, latitude)
                tMapView.setCenterPoint(longitude, latitude)
            }
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(
            provider: String,
            status: Int,
            extras: Bundle
        ) {
        }
    }

    private fun setGps() {
        val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
    }
}