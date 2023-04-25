package com.thunder.arbooks

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth


enum class ProviderType {
    BASIC,
    GOOGLE
}

class CerrarSesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cerrarsesion)

        //setup
        val bundle = intent.extras
        val email  = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        // Guardado de Datos

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

    }

    private fun setup(email: String, provider: String) {
        title = "Inicio"
        val emailTextView = findViewById<TextView>(R.id.emailTextView)
        val providerTextView = findViewById<TextView>(R.id.providerTextView)
        emailTextView.text = email
        providerTextView.text = provider

        val LogOutButton = findViewById<Button>(R.id.LogOutButton)
        LogOutButton.setOnClickListener {

            //borrado de datos
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}
