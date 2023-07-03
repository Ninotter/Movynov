package com.projetb3.movynov.dataclasses.credits

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName
import java.net.HttpURLConnection
import java.net.URL


class Cast (
    @SerializedName("adult"                ) var adult              : Boolean? = null,
    @SerializedName("gender"               ) var gender             : Int?     = null,
    @SerializedName("id"                   ) var id                 : Int?     = null,
    @SerializedName("known_for_department" ) var knownForDepartment : String?  = null,
    @SerializedName("name"                 ) var name               : String?  = null,
    @SerializedName("original_name"        ) var originalName       : String?  = null,
    @SerializedName("popularity"           ) var popularity         : Double?  = null,
    @SerializedName("profile_path"         ) var profilePath        : String?  = null,
    @SerializedName("cast_id"              ) var castId             : Int?     = null,
    @SerializedName("character"            ) var character          : String?  = null,
    @SerializedName("credit_id"            ) var creditId           : String?  = null,
    @SerializedName("order"                ) var order              : Int?     = null

){
    public var profileImage : Drawable? = null

    public fun loadProfileImage(){
        if(profilePath != null){
            profileImage = drawableFromUrl("https://image.tmdb.org/t/p/w500" + profilePath)
        }
    }

    @Throws(Exception::class)
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