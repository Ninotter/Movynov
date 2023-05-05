package com.projetb3.movynov.viewmodels

import androidx.lifecycle.ViewModel
import com.projetb3.movynov.dataclasses.MediaMovie
import com.projetb3.movynov.dataclasses.User

class MainViewModel : ViewModel(){
    private lateinit var popularMovies : List<MediaMovie>
    private var connectedUser : User? = null


    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}