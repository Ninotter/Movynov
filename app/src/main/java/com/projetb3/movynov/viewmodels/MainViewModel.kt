package com.projetb3.movynov.viewmodels

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.projetb3.movynov.dataclasses.auth.LoginRegisterResult
import com.projetb3.movynov.dataclasses.auth.User
import com.projetb3.movynov.model.AuthModel
import com.projetb3.movynov.singleton.SingletonCurrentUser

class MainViewModel : ViewModel(){
    private val connectedUser : SingletonCurrentUser

    init{
        connectedUser = SingletonCurrentUser.getInstance()
    }


    fun login(email : String, password : String) : Boolean{
        val response = AuthModel().login(email, password)
        if (response.isSuccessful){
            val loginResult = Gson().fromJson(response.body!!.string(), LoginRegisterResult::class.java)
            val user = User(email, "TODO" ,loginResult.token!!) //TODO
            setConnectedUser(user)
            return true
        }else{
            return false
        }
    }

    fun register(email: String, password: String): Boolean {
        val response = AuthModel().register(email, password)
        if (response.isSuccessful){
            Gson().fromJson(response.body!!.string(), LoginRegisterResult::class.java)
            val user = User(email, response.body!!.string())
            setConnectedUser(user)
            return true
        }else{
            return false
        }
    }

    fun setConnectedUser(user : User){
        connectedUser.setUser(user)
    }

    fun getConnectedUser() : User? {
        return connectedUser!!.getUser()
    }

}