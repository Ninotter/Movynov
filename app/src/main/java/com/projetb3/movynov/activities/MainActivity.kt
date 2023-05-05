package com.projetb3.movynov.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.projetb3.movynov.R
import com.projetb3.movynov.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popular_layout)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
}