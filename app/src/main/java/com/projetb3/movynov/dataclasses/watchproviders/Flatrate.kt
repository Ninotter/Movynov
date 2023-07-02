package com.projetb3.movynov.dataclasses.watchproviders

import com.google.gson.annotations.SerializedName

data class Flatrate (
    @SerializedName("logo_path"        ) var logoPath        : String? = null,
    @SerializedName("provider_id"      ) var providerId      : Int?    = null,
    @SerializedName("provider_name"    ) var providerName    : String? = null,
    @SerializedName("display_priority" ) var displayPriority : Int?    = null
        )