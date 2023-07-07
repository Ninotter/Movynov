package com.projetb3.movynov.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.projetb3.movynov.R
import com.projetb3.movynov.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var drawerLayout : DrawerLayout

    private lateinit var emailField : EditText
    private lateinit var passwordField : EditText
    private lateinit var usernameField : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        drawerLayout = findViewById(R.id.login_drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.login_nav_view)
        DrawerBehavior().setDrawerOpenOnClick(drawerLayout, navigationView, this, findViewById(R.id.login_toolbar))

        emailField = findViewById(R.id.login_email)
        passwordField = findViewById(R.id.login_password)
        usernameField = findViewById(R.id.login_username)

        findViewById<Button>(R.id.login_button).setOnClickListener {
            if (verifyFields()){
                tryLogin()
            }
        }

        findViewById<Button>(R.id.register_button).setOnClickListener {
            if (verifyFields()){
                tryRegister()
            }
        }
    }

    private fun tryLogin(){
        try{
            GlobalScope.launch {
                if (viewModel.login(emailField.text.toString(), passwordField.text.toString())){
                    runOnUiThread(Runnable {
                        afterAuth()
                    })
                } else {
                    runOnUiThread(Runnable {
                        val errorTextView = findViewById<TextView>(R.id.login_error_text)
                        errorTextView.visibility = TextView.VISIBLE
                        errorTextView.text = "Erreur lors de la connexion."
                    })
                }
            }
        }catch(ex : Exception){
            runOnUiThread(Runnable {
                val errorTextView = findViewById<TextView>(R.id.login_error_text)
                errorTextView.visibility = TextView.VISIBLE
                errorTextView.text = "Erreur lors de la connexion."
            })
        }
    }

    private fun tryRegister(){
        GlobalScope.launch {
            try{
                if(viewModel.register(emailField.text.toString(), passwordField.text.toString(), usernameField.text.toString())){
                    afterAuth()
                } else {
                    runOnUiThread(Runnable {
                        val errorTextView = findViewById<TextView>(R.id.login_error_text)
                        errorTextView.visibility = TextView.VISIBLE
                        errorTextView.text = "Erreur lors de l'inscription."
                    })
                }
            }catch(ex : Exception){
                runOnUiThread(Runnable {
                    val errorTextView = findViewById<TextView>(R.id.login_error_text)
                    errorTextView.visibility = TextView.VISIBLE
                    errorTextView.text = "Erreur lors de l'inscription."
                })
            }
        }
    }

    private fun verifyFields() : Boolean{
        val email = emailField.text.toString()
        val password = passwordField.text.toString()
        val errorTextView = findViewById<TextView>(R.id.login_error_text)
        val emailRegex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (!emailRegex.matches(email)){
            errorTextView.text = "Veuillez entrer une adresse email valide."
            errorTextView.visibility = TextView.VISIBLE
            return false;
        }
        if (!email.isNotEmpty() && !password.isNotEmpty()){
            errorTextView.text = "Veuillez remplir tous les champs."
            errorTextView.visibility = TextView.VISIBLE
            return false;
        }
        return true;
    }

    private fun afterAuth(){
        val intent = Intent(this, PopularActivity::class.java)
        //clears activity stack
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return MenuBehavior().onNavigationItemSelected(item, this)
    }
}