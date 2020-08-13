package com.example.tmapclone_kickgoing

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.skt.Tmap.TMapPoint
import kotlinx.android.synthetic.main.activity_find_load.*
import kotlinx.android.synthetic.main.activity_main.*
import org.techtown.mapservicewithtmapapi.SearchPOI

class FindLoad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_load)

        var searchIntent = intent
        var searchFlag = Integer.parseInt(searchIntent.getStringExtra("key"))
        var arrP = ArrayList<TMapPoint>()
        arrP.add(TMapPoint(0.0, 0.0))
        arrP.add(TMapPoint(0.0, 0.0))


        startBtn.setOnClickListener {
            SearchPOI(searchFlag, searchResult_list_item, startEdt.text.toString(), this, startEdt, arrivalEdt, arrP).execute()
        }
        arrivalBtn.setOnClickListener {
            SearchPOI(searchFlag, searchResult_list_item, arrivalEdt.text.toString(), this, startEdt, arrivalEdt, arrP).execute()
        }

        doFindLoad.setOnClickListener {
            val rIntent = Intent(this, MainActivity::class.java)
            var passValue = ArrayList<Double>()
            passValue.add(arrP[0].longitude)
            passValue.add(arrP[0].latitude)
            passValue.add(arrP[1].longitude)
            passValue.add(arrP[1].latitude)
            rIntent.putExtra("Position", passValue)

            setResult(Activity.RESULT_OK, rIntent)
            finish()
        }
    }// end of onCreate()
}