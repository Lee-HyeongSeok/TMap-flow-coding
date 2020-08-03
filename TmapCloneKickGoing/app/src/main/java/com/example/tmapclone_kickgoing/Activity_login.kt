package com.example.tmapclone_kickgoing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class Activity_login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        registerFB()    //FireBase 등록

        var log_dialog: LinearLayout =
            View.inflate(this@Activity_login, R.layout.activity_login, null) as LinearLayout
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@Activity_login)
        val dialogIDText = log_dialog.findViewById<EditText>(R.id.id)
        val dialogPASSText = log_dialog.findViewById<EditText>(R.id.password)
        //val dialogRegi = log_dialog.findViewById<TextView>(R.id.Register_Btn)
        // 2020-07-30(목) 이형석 수정
        // var user_password: String? = null
        var checking_id: Boolean = false

        btnID.setOnClickListener {
            val str_id: String = dialogIDText.text.toString()
            val str_pass: String = dialogPASSText.text.toString()

            // firebase realtime DB에 접근하는 부분
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef: DatabaseReference = database.getReference("user")

            // DB에 접근해서 로그인한 아이디와 비밀번호 일치성 검사, 운전면허 여부 검사
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (list in snapshot.children) {
                        if (str_id.equals(list.key)) { // 사용자 입력 아이디가 하나라도 중복될 경우
                            checking_id = true // 중복이라는 표시
                        }
                    }
                    if (checking_id == true) { // 존재하는 아이디
                        // 해당 아이디의 비밀번호를 DB에서 가져온다.
                        var temp: String = snapshot.child(str_id).child("password").getValue() as String
                        // 입력한 비밀번호와 DB에서 가져온 비밀번호 일치 시
                        if (str_pass.equals(temp)) { // 로그인 성공
                            Toast.makeText(applicationContext, "${str_id} 님이 로그인하셨습니다.", Toast.LENGTH_SHORT).show()
                        }
                        // 아이디는 일치, 비밀번호가 불일치할 때
                        else{
                            Toast.makeText(applicationContext, "비밀번호 불일치", Toast.LENGTH_SHORT).show()
                        }
                        checking_id = false // 재로그인을 위해 로그인 offset을 처음 상태로 돌려 놓는다.
                    } else {
                        // 존재하지 않는 아이디
                        Toast.makeText(applicationContext, "존재하지 않는 아이디임, 재로그인", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // 데이터에 대한 접근이 취소되었을 때 동작하는 함수
                }
            })
            /* 2020-07-30(목) 이형석 수정
            if (str_pass.equals(user_password)) {
                Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                tMapView!!.invalidate() // 2020-07-30(목) 작성
            }

             */
        }
    }   //end of onCreate()
    private fun registerFB(){
        auth = Firebase.auth
        auth.signInWithEmailAndPassword("1170313@naver.com", "silver12")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}