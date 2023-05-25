package com.projetb3.movynov.viewmodels

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.User

class MainViewModel : ViewModel(){
    private lateinit var popularMovies : List<MediaMovie>
    private var connectedUser : User? = null
    private var mainViewType : MainViewType = MainViewType.POPULAR

    //solution enviseagable? TODO
    enum class MainViewType {
        POPULAR,
        WATCHLIST,
        SEARCH,
        PROFILE
    }
    public fun changeNavigation(intent:Intent){
        //TODO
        /*if(intent = pageWatchlist){
            mainViewType = MainViewType.WATCHLIST
        }*/ //etc
    }

    fun getConnectedUser() : User? {
        return connectedUser
    }

    fun getMainViewType() : MainViewType {
        return mainViewType
    }

    fun getPopularMovies() : List<MediaMovie> {
        return popularMovies
    }
}