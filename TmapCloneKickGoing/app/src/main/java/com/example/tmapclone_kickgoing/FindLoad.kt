package com.example.tmapclone_kickgoing

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_find_load.*

class FindLoad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_load)

        val geocoder: Geocoder = Geocoder(this)
        
        search.setOnClickListener {
            var startlist: List<Address>? = null
            var endlist: List<Address>? = null

            startlist =geocoder.getFromLocationName(startAddress.text.toString(),10)
            endlist=geocoder.getFromLocationName(endAddress.text.toString(),10)

            val startaddress: Address =startlist.get(0)
            val endaddress: Address =endlist.get(0)


            val uri = Uri.parse(
                "kakaomap://route?sp=" + startaddress.latitude.toString()
                        + "," + startaddress.longitude.toString() + "&ep="
                        + endaddress.latitude.toString() + ","
                        + endaddress.longitude.toString() + "&by=FOOT"
            )
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}