package com.example.tmapclone_kickgoing

object User {
    var Login:Boolean = false
    var UserName:String = "QuickGoing"
    var UserEmail:String = "로그인해라"

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
}