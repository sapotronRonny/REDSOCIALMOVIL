package com.example.proyecto

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Aquí podrías cargar los datos reales del usuario si los tienes
        val nombre = "Juan Pérez"
        val correo = "juan@example.com"

        findViewById<TextView>(R.id.txtNombre).text = nombre
        findViewById<TextView>(R.id.txtEmail).text = "Correo: $correo"
    }
}
