package com.example.quickgoing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_event.*

class RegisterEvent : AppCompatActivity() {

    var check:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_event)

        val id:EditText = findViewById(R.id.input_id) // 아이디
        val pass:EditText = findViewById(R.id.input_pass) // 비밀번호
        val drive:EditText = findViewById(R.id.drive_pass) // 운전면허 번호



        // 아이디 유효성 검사   user(root)-id(child)-pass/dirve_pass(child)


        Positive_Btn.visibility = View.VISIBLE
        Positive_Btn.setOnClickListener {

            // 사용자 입력 string
            var id_str:String = id.text.toString()
            var pass_str:String = pass.text.toString()
            var drive_str:String = drive.text.toString()

            val database:FirebaseDatabase = FirebaseDatabase.getInstance()
            var myRef:DatabaseReference = database.getReference("users")
            myRef.child(id_str).setValue(pass_str)
            myRef.child(id_str).setValue(drive_str)

            var str:String ?= myRef.child(id_str).key
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()


            finish()
        }
        Negative_Btn.setOnClickListener {
            finish()
        }
        // "중복 체크" 버튼을 눌렀을 때
        check_Btn.setOnClickListener {

            // 사용자 입력 string
            var id_str:String = id.text.toString()
            var pass_str:String = pass.text.toString()


            if(CheckedID(id_str)){
                Toast.makeText(this, "아이디 있음", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "아이디 사용가능", Toast.LENGTH_SHORT).show()
                if(!pass_str.equals(null)){
                    Positive_Btn.visibility = View.VISIBLE
                }
                else{
                    Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun CheckedID(id:String):Boolean{
        val database:FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef:DatabaseReference = database.getReference("users")
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()

        if(myRef.child(id).key == id){
            Toast.makeText(this, myRef.child(id).key, Toast.LENGTH_SHORT).show()
            return true
        }
        else{
            return check
        }

    }
}
