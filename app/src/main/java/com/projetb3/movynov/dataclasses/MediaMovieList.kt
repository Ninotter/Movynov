package com.projetb3.movynov.dataclasses

import com.google.gson.annotations.SerializedName

data class MediaMovieList(
    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var results      : ArrayList<MediaMovie> = arrayListOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null
)
