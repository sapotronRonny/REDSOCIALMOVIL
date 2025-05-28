package com.example.proyecto

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var txtNombre: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtGenero: TextView
    private lateinit var txtNoticia: TextView
    private lateinit var txtProvincia: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Inicializar Firebase
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Obtener referencias a los TextView
        txtNombre = findViewById(R.id.txtNombre)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtGenero = findViewById(R.id.txtGenero)
        txtNoticia = findViewById(R.id.txtNoticia)
        txtProvincia = findViewById(R.id.txtProvincia)

        cargarDatosUsuario()
    }

    private fun cargarDatosUsuario() {
        val uid = auth.currentUser?.uid

        if (uid != null) {
            db.collection("usuarios").document(uid).get()
                .addOnSuccessListener { documento ->
                    if (documento.exists()) {
                        val usuario = documento.toObject(Usuario::class.java)
                        usuario?.let {
                            txtNombre.text = "Nombre: ${it.nombre}"
                            txtCorreo.text = "Correo: ${it.correo}"
                            txtGenero.text = "Género: ${it.genero}"
                            txtNoticia.text = "Interés en noticias: ${it.noticia}"
                            txtProvincia.text = "Provincia: ${it.provincias}"
                        }
                    } else {
                        Toast.makeText(this, "No se encontraron datos del usuario", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al cargar datos: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }
    }
}

