package com.example.tmapclone_kickgoing

import com.facebook.login.Login
import com.google.firebase.auth.FirebaseAuth

object User {
    var Login:Boolean = false
    var facebookLogin:Boolean = false
    var UserName:String = "QuickGoing"
    var UserEmail:String = "로그인해라"

    fun setFBlogin(log:Boolean){
        facebookLogin = log
    }

    fun setUserLog(log: Boolean){
        Login = log
    }
    fun setName(name:String){
        UserName = name
    }
    fun setEmail(email:String){
        UserEmail = email
    }


    fun getName(): String {
        return UserName
    }
    fun getEmail(): String {
        return UserEmail
    }
    fun getUserLog(): Boolean{
        return Login
    }
    fun getFBUserLog():Boolean{
        return facebookLogin
    }
}