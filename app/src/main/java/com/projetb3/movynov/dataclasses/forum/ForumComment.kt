package com.projetb3.movynov.dataclasses.forum

import com.google.gson.annotations.SerializedName
import com.projetb3.movynov.dataclasses.auth.User

data class ForumComment (
    @SerializedName("content"   ) var content   : String?  = null,
    @SerializedName("idUser"    ) var idUser    : User?  = User(),
    @SerializedName("spoilers"  ) var spoilers  : Boolean? = null,
    @SerializedName("uuid"      ) var uuid      : String?  = null,
    @SerializedName("createdAt" ) var createdAt : String?  = null,
    @SerializedName("updatedAt" ) var updatedAt : String?  = null,
    @SerializedName("idPost"    ) var idPost    : ForumPost?  = ForumPost()
)