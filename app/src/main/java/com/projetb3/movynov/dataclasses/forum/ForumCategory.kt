package com.projetb3.movynov.dataclasses.forum

import com.google.gson.annotations.SerializedName

data class ForumCategory (
    @SerializedName("title"       ) var title       : String? = null,
    @SerializedName("description" ) var description : String? = null
        )