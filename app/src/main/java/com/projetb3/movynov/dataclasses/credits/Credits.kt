package com.projetb3.movynov.dataclasses.credits

import com.google.gson.annotations.SerializedName

data class Credits (
    @SerializedName("cast" ) var cast : ArrayList<Cast> = arrayListOf(),
    @SerializedName("crew" ) var crew : ArrayList<Crew> = arrayListOf()
)