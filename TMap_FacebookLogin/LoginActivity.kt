package kr.psk.kpu.tmapmarker

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

//KP240QGriy0/mvRnS87fM9uRPNU=

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth=FirebaseAuth.getInstance()
        callbackManager= CallbackManager.Factory.create()

        facebook_login.setOnClickListener{
            facebookLogin()
        }

        facebook_logout.setOnClickListener{
            facebookLogout()
        }
    }

    private fun facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult?) {
                //페이스북 로그인 성공
                handleFacebookAccessToken(result?.accessToken)
                Toast.makeText(applicationContext,"로그인 성공",Toast.LENGTH_SHORT).show()
            }
            override fun onCancel() {
                //페이스북 로그인 취소
                updateUI(null)
            }

            override fun onError(error: FacebookException?) {
                //페이스북 로그인 실패
                updateUI(null)
            }
        })
    }

    // callbackManager에게 로그인 결과를 넘겨줌
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken?) {
        Log.d("MainActivity", "handleFacebookAccessToken:$token")
        if (token != null) {
            val credential = FacebookAuthProvider.getCredential(token.token)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("MainActivity", "signInWithCredential:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.w("MainActivity", "signInWithCredential:failure", task.exception)
                        Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        // 로그인 되어있는 유저를 확인
        if(auth.currentUser!=null){
            Toast.makeText(this,"로그인 상태입니다.",Toast.LENGTH_SHORT).show()
            updateUI(auth.currentUser)
        }
    }


    // user의 정보를 가져와 로그인 여부를 확인
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            status.text = user.displayName
            //detail.text = user.photoUrl.toString()
        } else {
            status.setText("로그인 안됨")
            //detail.text = "photoURL"
        }
    }

   private fun facebookLogout() {
        auth.signOut()
        LoginManager.getInstance().logOut()
        updateUI(null)
        Toast.makeText(this,"로그아웃",Toast.LENGTH_SHORT).show()
    }

}