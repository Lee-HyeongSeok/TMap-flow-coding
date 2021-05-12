package com.example.tmapclone_kickgoing


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import com.example.tmapclone_kickgoing.MainActivity.Companion.auth
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.util.*
import kotlin.random.Random.Default.Companion


class Activity_login : AppCompatActivity() {
    // [START declare_auth]

    private lateinit var callbackManager: CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient


    private fun registerFB(){
        var auth2 = Firebase.auth
        auth2.signInWithEmailAndPassword("1170313@naver.com", "silver12")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    val user = auth2.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();


        btLoginFacebook.setOnClickListener{
            facebookLogin()
        }

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth

        googleButton.setOnClickListener {
            signIn()
        }

        //val dialogRegi = log_dialog.findViewById<TextView>(R.id.Register_Btn)
        // 2020-07-30(목) 이형석 수정
        // var user_password: String? = null
        var checking_id: Boolean = false

        registerFB()
        btnLogin.setOnClickListener {
            val str_id: String = etEmail.text.toString()
            val str_pass: String = etPassword.text.toString()

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

                           // User.setName(str_id)
                            User.setName(str_id.toString())
                            User.setEmail(auth.currentUser!!.email.toString())
                            User.setUserLog(true)
                            Log.d("showInformed", "firbname: "+ User.getName())
                            Log.d("showInformed", "firbemail: " + auth.currentUser!!.email.toString())

                            finish()
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

    private fun facebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult?) {
                //페이스북 로그인 성공
                handleFacebookAccessToken(result?.accessToken)
                Toast.makeText(applicationContext,"페이스북 로그인 성공",Toast.LENGTH_SHORT).show()
                var user:FirebaseUser? = auth.currentUser
                User.setName(user!!.displayName.toString())
                User.setEmail("FaceBook")
                User.setUserLog(true)
                User.setFBlogin(true)

                Log.d("showInformed", "name: "+ user.displayName.toString())
                Log.d("showInformed", "email" + auth.currentUser!!.email.toString())
                finish()
            }
            override fun onCancel() {
                //페이스북 로그인 취소
                //updateUI(null)
            }

            override fun onError(error: FacebookException?) {
                //페이스북 로그인 실패
                //updateUI(null)
            }
        })
    }


    private fun handleFacebookAccessToken(token: AccessToken?) {
        Log.d("MainActivity", "handleFacebookAccessToken:$token")
        if (token != null) {
            val credential = FacebookAuthProvider.getCredential(token.token)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("MainActivity", "signInWithCredential:success")
                        //updateUI(user)
                    } else {
                        Log.w("MainActivity", "signInWithCredential:failure", task.exception)
                        Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }
    }

    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        // Check if user is signed in (non-null) and update UI accordingly.
        Log.d(TAG, "firebaseAuthWithGoogle:" + currentUser?.displayName)
        if(currentUser !=null){
            Toast.makeText(this,"로그인 상태입니다.",Toast.LENGTH_SHORT).show()
            Log.d("@@@@", currentUser!!.email.toString())

            User.setName(currentUser.displayName.toString())
            User.setEmail(currentUser.email.toString())
            User.setUserLog(true)
            finish()
        }
    }
    // [END on_start_check_user]


    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val Nav_View: NavigationView = findViewById(R.id.nav_view)
            val headerView = Nav_View.getHeaderView(0)
            var name: TextView = headerView.findViewById(R.id.name)
            var email: TextView = headerView.findViewById(R.id.email)
            name.text = user.displayName.toString()
            email.text = user.email.toString()
            //detail.text = user.photoUrl.toString()
        } else {

            //detail.text = "photoURL"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
        else{
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext,"구글로그인 성공",Toast.LENGTH_SHORT).show()
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    var user:FirebaseUser? = auth.currentUser
                    User.setName(user!!.displayName.toString())
                    User.setEmail(user!!.email.toString())
                    User.setUserLog(true)

                    Log.d("showInformed", "name: "+ auth.currentUser!!.displayName.toString())
                    Log.d("showInformed", "email" + auth.currentUser!!.email.toString())
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
    // [END auth_with_google]

    // [START signin]
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    private fun signOut() {
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
        }
    }
    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }


}