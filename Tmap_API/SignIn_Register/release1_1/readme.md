## * Tmap API를 이용한 로그인/회원가입 서비스 만들기   
#### 코드 출처 : https://github.com/leehyeongseck/TMap-flow-coding/tree/dev/Tmap_API/SignIn_Register/release1_1   
#### API 출처 : https://tmapapi.sktelecom.com/index.html   
#### 2차 릴리즈 날짜 : 2020-08-05(수)   

#### 변경/추가 내용   
		- 구글 로그인 추가   
		- Firebase Google 로그인 사용   

#### onCreate() 외부에 선언   

```kotlin
private lateinit var googleSignInClient: GoogleSignInClient


// 중략 

private fun signIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle: "+ account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e: ApiException){
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    LogCheck = true
                    Log.d(TAG, "signInWithCredential: success")
                }
                else{
                    Log.w(TAG, "signInWithCredential: failure", task.exception)
                }
            }
    }

    companion object{
        private const val TAG="GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

```   

#### onCreate() 내부에 선언   

```kotlin
val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

// 구글 로그인 버튼 누를 시
googleLogBtn.setOnClickListener {
                signIn() // 메소드 호출 
            }
```