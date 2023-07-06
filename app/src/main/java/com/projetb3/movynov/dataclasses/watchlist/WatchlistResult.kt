package com.projetb3.movynov.dataclasses.watchlist

import com.google.gson.annotations.SerializedName

data class WatchlistResult(
    @SerializedName("status"  ) var status  : Int? = null,
    @SerializedName("idMedia" ) var idMedia : Int? = null
)
