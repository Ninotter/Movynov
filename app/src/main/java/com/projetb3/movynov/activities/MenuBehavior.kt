package com.projetb3.movynov.activities

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.projetb3.movynov.R

class MenuBehavior {

    public fun onNavigationItemSelected(item: MenuItem, activity : AppCompatActivity): Boolean {
        when (item.itemId) {
            R.id.nav_login -> {
                val intent = Intent(activity, LoginActivity::class.java)
                activity.startActivity(intent)
                return true
            }
        }
        return false
    }
}