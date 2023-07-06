package com.projetb3.movynov.singleton

import com.projetb3.movynov.dataclasses.auth.User

class SingletonCurrentUser {
    private var user : User? = null


    companion object {
        @Volatile
        private var instance: SingletonCurrentUser? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: SingletonCurrentUser().also { instance = it }
            }
    }

    public fun getUser() : User? {
        return user
    }

    public fun setUser(user : User){
        this.user = user
    }
}