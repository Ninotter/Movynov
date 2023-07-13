package com.projetb3.movynov.dataclasses.auth

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class User(
    @SerializedName("id"   ) var id  :Int?      = null,
    var email     :String?         = null,
    @SerializedName("pseudo"   ) var username  :String?      = null,
    var token      :String?               = null
)