package com.example.tmapclone_kickgoing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register_event.*

class RegisterEvent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_event)

        // 2020-07-28일 화요일 작성
        val id:EditText = findViewById(R.id.input_id) // 아이디 입력받는 에디터
        val pass:EditText = findViewById(R.id.input_pass) // 비밀번호 입력받는 에디터
        val drive:EditText = findViewById(R.id.drive_pass) // 면허 번호 입력받는 에디터
        var checking_id:Boolean = false


        // 재회원가입 offset으로 방지[x]
        // 아이디 중복체크로 아이디 여부 확인[o]   // 2020-07-28일 화요일 작성
        // 아이디 중복체크 완료시 확인버튼 보이도록 설정[o] // 2020-07-28일 화요일 작성
        // 로그인 부분 완료 시키기[o] // 2020-07-28일 화요일 작성

        // 확인버튼 누를 시 동작
        registID.setOnClickListener {
            if(pass.text.toString() == ""){
                Toast.makeText(applicationContext, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                // 입력된 아이디, 패스워드, 면허 등록
                val u_id: String = id.text.toString()
                val u_pass: String = pass.text.toString()
                val u_drive: String = drive.text.toString()
                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                val myRef: DatabaseReference = database.getReference("user")
                var data = HashMap<String, String>()
                data.put("password", u_pass)
                data.put("drive_pass", u_drive)
                myRef.child(u_id).setValue(data)
                finish()
            }
        }


        // 중복 체크 버튼
        check_Btn.setOnClickListener {
            val u_id:String = id.text.toString()
            val u_pass:String = pass.text.toString()
            val u_drive:String = drive.text.toString()
            val database:FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef:DatabaseReference = database.getReference("user")

            // DB에 이벤트 발생 시 동작하는 함수
            myRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(list in snapshot.children){
                        if(u_id.equals(list.key)){ // 사용자 입력 아이디가 하나라도 중복될 경우
                            checking_id = true // 중복이라는 표시
                        }
                    }
                    /*
                    if(checking_id == true){
                        Toast.makeText(applicationContext, "아이디 중복", Toast.LENGTH_SHORT).show()
                        checking_id = false
                    }
                    else{ // false일 떄
                        Toast.makeText(applicationContext, "사용 가능 아이디", Toast.LENGTH_SHORT).show()
                        registID.isClickable = true
                    }

                     */
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
            if(checking_id == true){
                Toast.makeText(applicationContext, "아이디 중복", Toast.LENGTH_SHORT).show()
            }
            else{ // false일 떄
                Toast.makeText(applicationContext, "사용 가능 아이디", Toast.LENGTH_SHORT).show()
                registID.isClickable = true
            }
        }
    }//end of onCreate()



}