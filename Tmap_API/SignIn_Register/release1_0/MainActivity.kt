package com.example.kick_going_tmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 사용자 인증 부분 : 앱 관리자
        // 2020-07-28일 화요일 작성
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword("email", "password") // 하드 코딩 처리 나중에
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        val user = mAuth.currentUser
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                    }
                }



        // 2020-07-28일 화요일 작성
        val linear:RelativeLayout = findViewById(R.id.mapLayout) as RelativeLayout

        // 2020-07-28일 화요일 작성
        val tmapView:TMapView = TMapView(this)
        tmapView.setSKTMapApiKey("your app key")
        linear.addView(tmapView)

        // 2020-07-28일 화요일 작성
        // 대여하기 버튼 누를 시 동작
        Rental_Btn.setOnClickListener {
            var log_dialog:LinearLayout = View.inflate(this@MainActivity, R.layout.login_dialog, null) as LinearLayout
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            val dialogIDText = log_dialog.findViewById<EditText>(R.id.edit_id)
            val dialogPASSText = log_dialog.findViewById<EditText>(R.id.edit_pass)
            val dialogRegi = log_dialog.findViewById<TextView>(R.id.Register_Btn)
            val intent:Intent = Intent(this, RegisterEvent::class.java)
            var user_password: String?=null
            var checking_id:Boolean=false

            builder.setView(log_dialog)
                .setPositiveButton("확인"){dialogInterface, i ->
                    val str_id:String = dialogIDText.text.toString()
                    val str_pass:String = dialogPASSText.text.toString()

                    val database:FirebaseDatabase = FirebaseDatabase.getInstance()
                    val myRef:DatabaseReference = database.getReference("user")
                    // DB에 접근해서 로그인한 아이디와 비밀번호 일치성 검사, 운전면허 여부 검사
                    myRef.addValueEventListener(object: ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for(list in snapshot.children){
                                if(str_id.equals(list.key)){ // 사용자 입력 아이디가 하나라도 중복될 경우
                                    checking_id = true // 중복이라는 표시
                                }
                            }
                            if(checking_id == true){
                                // 존재하는 아이디
                                var temp: String = snapshot.child(str_id).child("password").getValue() as String // 해당 아이디의 비밀번호 가져오기
                                if(str_pass.equals(temp)){
                                    Toast.makeText(applicationContext, "${str_id} 님이 로그인하셨습니다.", Toast.LENGTH_SHORT).show()
                                }
                                checking_id = false
                            }
                            else{
                                // 존재하지 않는 아이디
                                Toast.makeText(applicationContext, "존재하지 않는 아이디임, 재로그인", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })

                    if(str_pass.equals(user_password)){
                        Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("취소"){dialogInterface, i ->
                    dialogInterface.dismiss()
                }.show()

            // 다이얼로그 상자에 회원가입 버튼 누를 시 회원가입 페이지로 인텐트 전환
            dialogRegi.setOnClickListener {
                startActivity(intent)
            }
        }
    }

}
