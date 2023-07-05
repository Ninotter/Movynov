package com.projetb3.movynov.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import com.projetb3.movynov.R
import com.projetb3.movynov.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var emailField : EditText
    private lateinit var passwordField : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        emailField = findViewById(R.id.login_email)
        passwordField = findViewById(R.id.login_password)

        findViewById<Button>(R.id.login_button).setOnClickListener {
            if (verifyFields()){
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

            }
        }

        findViewById<Button>(R.id.register_button).setOnClickListener {
            if (verifyFields()){
                GlobalScope.launch {
                if (viewModel.register(emailField.text.toString(), passwordField.text.toString())){
                    afterAuth()
                } else {
                    runOnUiThread(Runnable {
                        val errorTextView = findViewById<TextView>(R.id.login_error_text)
                        errorTextView.visibility = TextView.VISIBLE
                        errorTextView.text = "Erreur lors de l'inscription."
                    })
                }}
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
}