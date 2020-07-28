## * Tmap API를 이용한 로그인/회원가입 서비스 만들기   
#### 코드 출처 : https://github.com/leehyeongseck/TMap-flow-coding/tree/dev   
#### API 출처 : https://tmapapi.sktelecom.com/index.html   
#### 1차 릴리즈 날짜 : 2020-07-28(화)   


#### 로그인 요구사항   
		* 사용자가 존재하지 않는 아이디로 로그인 시 로그인이 불가해야 한다.   
		* 사용자가 존재하지 않는 아이디로 로그인 시도 시 오류 메시지를 출력한다.   
		* 사용자가 입력한 비밀번호가 일치하지 않을 때 로그인이 불가해야 한다.   
		* 사용자가 로그인을 성공했을 때 사용자 아이디와 함께 로그인이 성공했음을 표시해야 한다.   
		
	

#### 회원가입 요구사항   
		* 사용자는 중복된 아이디를 사용할 수 없다.   
		* 사용자는 확인버튼을 누르기전 아이디 중복확인 과정을 거쳐야 한다.   
		* 사용자는 취소버튼을 누르면 이전의 메인 인텐트로 복귀할 수 있어야 한다.   
		* 사용자가 취소버튼을 누르면 현재 진행중이던 아이디와 비밀번호를 초기화 된다.   
		* 중복확인을 거친 사용자가 입력한 아이디와 비밀번호는 Firebase RealTime DB에 등록되어야 한다.   
		* 중복확인을 거치지 않으면 확인버튼은 보이지 않는다.   


#### RegisterEvent.kt   

```kotlin
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
        Positive_Btn.visibility = View.GONE
        Positive_Btn.setOnClickListener {
            // 입력된 아이디, 패스워드, 면허 등록
            val u_id:String = id.text.toString()
            val u_pass:String = pass.text.toString()
            val u_drive:String = drive.text.toString()
            val database:FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef:DatabaseReference = database.getReference("user")
            var data = HashMap<String, String>()
            data.put("password", u_pass)
            data.put("drive_pass", u_drive)
            myRef.child(u_id).setValue(data)
            finish()
        }

        // 취소버튼 누를 시 동작
        Negative_Btn.setOnClickListener {
            finish()
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
                    if(checking_id == true){
                        Toast.makeText(applicationContext, "아이디 중복", Toast.LENGTH_SHORT).show()
                        checking_id = false
                    }
                    else{
                        Toast.makeText(applicationContext, "사용 가능 아이디", Toast.LENGTH_SHORT).show()
                        Positive_Btn.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

}

```   

#### MainActivity.kt   

```kotlin

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 사용자 인증 부분 : 앱 관리자
        // 2020-07-28일 화요일 작성
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword("your email", "your password") // 하드 코딩 처리 나중에
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
        tmapView.setSKTMapApiKey("l7xxca4265e638094d98b6baab6062b4d7a2")
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

```