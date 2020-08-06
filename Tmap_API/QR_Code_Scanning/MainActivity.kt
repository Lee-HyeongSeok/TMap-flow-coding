package com.example.qr_code

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            var intentIntegrator:IntentIntegrator = IntentIntegrator(this)
            intentIntegrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result:IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null){
            if(result.contents == null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Scanned"+result.contents, Toast.LENGTH_SHORT).show()
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
