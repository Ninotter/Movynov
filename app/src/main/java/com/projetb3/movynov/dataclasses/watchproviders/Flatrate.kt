package com.projetb3.movynov.dataclasses.watchproviders

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class Flatrate (
    @SerializedName("logo_path"        ) var logoPath        : String? = null,
    @SerializedName("provider_id"      ) var providerId      : Int?    = null,
    @SerializedName("provider_name"    ) var providerName    : String? = null,
    @SerializedName("display_priority" ) var displayPriority : Int?    = null
        ){
    var logoImage : Drawable? = null
    public fun loadLogoImage(){
        if(logoPath != null){
            logoImage = drawableFromUrl("https://image.tmdb.org/t/p/w500" + logoPath)
        }
    }

    @Throws(IOException::class)
    private fun drawableFromUrl(url: String?): Drawable? {
        if (url == null || url.isEmpty()) return null
        val x: Bitmap
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val input = connection.inputStream
        try{
            x = BitmapFactory.decodeStream(input)
        }
        catch(e: Exception){
            return null
        }
        return BitmapDrawable(Resources.getSystem(), x)
    }
}