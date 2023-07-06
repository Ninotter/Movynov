package com.projetb3.movynov.activities

import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import com.projetb3.movynov.R

class DrawerBehavior {
    public fun setDrawerOpenOnClick (drawerLayout : androidx.drawerlayout.widget.DrawerLayout, navigationView : NavigationView, activity : androidx.appcompat.app.AppCompatActivity, toolbar: androidx.appcompat.widget.Toolbar) {
        navigationView.setNavigationItemSelectedListener(activity as NavigationView.OnNavigationItemSelectedListener)

        val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}