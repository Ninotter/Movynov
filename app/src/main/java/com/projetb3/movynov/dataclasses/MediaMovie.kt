package com.projetb3.movynov.dataclasses

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName
import com.projetb3.movynov.dataclasses.credits.Credits
import com.projetb3.movynov.dataclasses.videos.Videos
import com.projetb3.movynov.dataclasses.watchproviders.WatchProviders
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Date


class MediaMovie(
    @SerializedName("adult"                 ) var adult               : Boolean?                       = null,
    @SerializedName("backdrop_path"         ) var backdropPath        : String?                        = null,
    //@SerializedName("belongs_to_collection" ) var belongsToCollection : String?                        = null,
    @SerializedName("budget"                ) var budget              : Int?                           = null,
    @SerializedName("genres"                ) var genres              : ArrayList<Genres>              = arrayListOf(),
    @SerializedName("homepage"              ) var homepage            : String?                        = null,
    @SerializedName("id"                    ) var id                  : Int?                           = null,
    @SerializedName("imdb_id"               ) var imdbId              : String?                        = null,
    @SerializedName("original_language"     ) var originalLanguage    : String?                        = null,
    @SerializedName("original_title"        ) var originalTitle       : String?                        = null,
    @SerializedName("overview"              ) var overview            : String?                        = null,
    @SerializedName("popularity"            ) var popularity          : Double?                        = null,
    @SerializedName("poster_path"           ) var posterPath          : String?                        = null,
    @SerializedName("release_date"          ) var releaseDate         : String?                        = null,
    @SerializedName("revenue"               ) var revenue             : Long?                           = null,
    @SerializedName("runtime"               ) var runtime             : Int?                           = null,
    @SerializedName("status"                ) var status              : String?                        = null,
    @SerializedName("tagline"               ) var tagline             : String?                        = null,
    @SerializedName("title"                 ) var title               : String?                        = null,
    @SerializedName("video"                 ) var video               : Boolean?                       = null,
    @SerializedName("vote_average"          ) var voteAverage         : Double?                        = null,
    @SerializedName("vote_count"            ) var voteCount           : Int?                           = null,
    @SerializedName("watch/providers"       ) var watchProviders     : WatchProviders?               = WatchProviders(),
    @SerializedName("credits"               ) var credits             : Credits?                       = Credits(),
    @SerializedName("videos"                ) var videos              : Videos?                        = Videos()

){
    var posterImage : Drawable? = null
    var backdropImage : Drawable? = null

    fun updatePosterImage(){
        if(posterPath != null){
            posterImage = drawableFromUrl("https://image.tmdb.org/t/p/w500" + posterPath)
        }
    }

    fun updatePosterImageFullResolution(){
        if(posterPath != null){
            posterImage = drawableFromUrl("https://image.tmdb.org/t/p/original" + posterPath)
        }
    }

    fun updateBackDropImage(){
        if(backdropPath != null){
            backdropImage = drawableFromUrl("https://image.tmdb.org/t/p/w500" + backdropPath)
        }
    }

    fun updateBackDropImageFullResolution(){
        if(backdropPath != null){
            backdropImage = drawableFromUrl("https://image.tmdb.org/t/p/original" + backdropPath)
        }
    }

    @Throws(IOException::class)
    fun drawableFromUrl(url: String?): Drawable? {
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
