package com.projetb3.movynov.dataclasses.videos

import com.google.gson.annotations.SerializedName

data class Videos (
    @SerializedName("results" ) var results : ArrayList<VideoResults> = arrayListOf()
)