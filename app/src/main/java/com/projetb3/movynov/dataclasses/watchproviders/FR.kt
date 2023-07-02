package com.projetb3.movynov.dataclasses.watchproviders

import com.google.gson.annotations.SerializedName

data class FR (
    @SerializedName("link"     ) var link     : String?             = null,
    @SerializedName("flatrate" ) var flatrate : ArrayList<Flatrate> = arrayListOf(),
    @SerializedName("buy"      ) var buy      : ArrayList<Buy>      = arrayListOf(),
    @SerializedName("rent"     ) var rent     : ArrayList<Rent>     = arrayListOf()
)
