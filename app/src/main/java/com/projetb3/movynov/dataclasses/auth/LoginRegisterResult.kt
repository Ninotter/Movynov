package com.projetb3.movynov.dataclasses.auth

import com.google.gson.annotations.SerializedName

data class LoginRegisterResult(
    @SerializedName("token"   ) var token   : String?    = null
)
