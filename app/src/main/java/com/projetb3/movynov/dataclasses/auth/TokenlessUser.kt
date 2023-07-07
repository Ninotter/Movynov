package com.projetb3.movynov.dataclasses.auth

import com.google.gson.annotations.SerializedName

data class TokenlessUser(
    @SerializedName("id"         ) var id         : Int?              = null,
    @SerializedName("email"      ) var email      : String?           = null,
    @SerializedName("roles"      ) var roles      : ArrayList<String> = arrayListOf(),
    @SerializedName("pseudo"     ) var pseudo     : String?           = null,
    @SerializedName("spoilers"   ) var spoilers   : Boolean?          = null,
    @SerializedName("uuid"       ) var uuid       : String?           = null,
)
