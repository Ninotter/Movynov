package com.projetb3.movynov.dataclasses.watchproviders

import com.google.gson.annotations.SerializedName

data class WatchProviders(
    @SerializedName("results" ) var results : ResultsWatchProviders? = ResultsWatchProviders()

)
