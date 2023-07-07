package com.projetb3.movynov.activities

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.auth.User

class MenuBehavior {



    public fun hideLoginMenu(navigationView: NavigationView, connectedUser: User) {
        navigationView.menu.findItem(R.id.nav_login).isVisible = false
        navigationView.menu.findItem(R.id.nav_logout).isVisible = true
        navigationView.menu.findItem(R.id.nav_username).isVisible = true
        navigationView.menu.findItem(R.id.nav_username).title = connectedUser.username
    }


    fun menuForNoUser(navigationView: NavigationView) {
        navigationView.menu.findItem(R.id.nav_logout).isVisible = false
        navigationView.menu.findItem(R.id.nav_username).isVisible = false
    }

    public fun onNavigationItemSelected(item: MenuItem, activity : AppCompatActivity): Boolean {
        when (item.itemId) {
            R.id.nav_login -> {
                val intent = Intent(activity, LoginActivity::class.java)
                activity.startActivity(intent)
                return true
            }
            R.id.nav_popular -> {
                val intent = Intent(activity, PopularActivity::class.java)
                activity.startActivity(intent)
                return true
            }
            R.id.nav_watchlist ->{
                val intent = Intent(activity, WatchlistActivity::class.java)
                activity.startActivity(intent)
                return true
            }
        }
        return false
    }

}