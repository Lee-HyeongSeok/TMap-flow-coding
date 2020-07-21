package org.techtown.kakaomapclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapview = MapView(this)
        val mapViewContainer = mapContainer as ViewGroup
        mapViewContainer.addView(mapview)
    }
}
