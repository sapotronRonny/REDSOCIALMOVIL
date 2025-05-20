package com.example.proyecto

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.proyecto.database.AppDatabase
import com.example.proyecto.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etContraseña: EditText
    private lateinit var rgGenero: RadioGroup
    private lateinit var checkPolitica: CheckBox
    private lateinit var checkDeportes: CheckBox
    private lateinit var checkCultura: CheckBox
    private lateinit var checkEducacion: CheckBox
    private lateinit var checkSalud: CheckBox
    private lateinit var spnProvincias: Spinner
    private lateinit var btnRegistro: Button
    private lateinit var cardResultado: CardView
    private lateinit var tvResultado: TextView
    private lateinit var btnLimpiar: Button
    private lateinit var db: AppDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro)

        inicializarBD()

        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etContraseña = findViewById(R.id.etContraseña)
        rgGenero = findViewById(R.id.rgGenero)
        checkPolitica = findViewById(R.id.chkPolitica)
        checkDeportes = findViewById(R.id.chkDeportes)
        checkCultura = findViewById(R.id.chkCultura)
        checkEducacion = findViewById(R.id.chkEducacion)
        checkSalud = findViewById(R.id.chkSalud)
        spnProvincias = findViewById(R.id.spnProvincias)
        btnRegistro = findViewById(R.id.btnRegistro)
        cardResultado = findViewById(R.id.cardResultado)
        tvResultado = findViewById(R.id.tvResultado)
        btnLimpiar = findViewById(R.id.btnLimpiar)

        val provincias = arrayOf(
            "Bolívar", "Chone", "El Carmen", "Flavio Alfaro", "Jama", "Jaramijó",
            "Jipijapa", "Junín", "Manta", "Montecristi", "Olmedo", "Paján",
            "Pedernales", "Pichincha", "Portoviejo", "Puerto López", "Rocafuerte",
            "Santa Ana", "Sucre", "Tosagua", "24 de Mayo"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provincias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnProvincias.adapter = adapter

        btnRegistro.setOnClickListener {
            enviarDatos()
        }

        btnLimpiar.setOnClickListener {
            limpiardatos()
        }
    }

    private fun enviarDatos() {
        val nombre = etNombre.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val contraseña = etContraseña.text.toString()

        // Validaciones
        if (nombre.isEmpty()) {
            etNombre.error = "Debe ingresar datos válidos"
            return
        } else if (nombre.length < 8) {
            etNombre.error = "El nombre debe tener al menos 8 caracteres"
            return
        } else if (nombre.any { it.isDigit() }) {
            etNombre.error = "El nombre no debe contener números"
            return
        } else {
            etNombre.error = null
        }

        if (correo.isEmpty()) {
            etCorreo.error = "Debe ingresar datos válidos"
            return
        } else if (!correo.contains("@") || !correo.endsWith("gmail.com")) {
            etCorreo.error = "Dominio no válido solo gmail.com permitido"
            return
        } else {
            etCorreo.error = null
        }

        if (contraseña.length < 8 || !contraseña.any { it.isDigit() } || !contraseña.any { it.isUpperCase() }) {
            etContraseña.error = "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número"
            return
        } else {
            etContraseña.error = null
        }

        val idGeneroSeleccionado = rgGenero.checkedRadioButtonId
        if (idGeneroSeleccionado == -1) {
            Toast.makeText(this, "Debe seleccionar un género", Toast.LENGTH_SHORT).show()
            return
        }

        val genero = findViewById<RadioButton>(idGeneroSeleccionado).text.toString()
        val provincias = spnProvincias.selectedItem.toString()

        val noticias = mutableListOf<String>().apply {
            if (checkDeportes.isChecked) add("Deportes")
            if (checkCultura.isChecked) add("Cultura")
            if (checkEducacion.isChecked) add("Educación")
            if (checkSalud.isChecked) add("Salud")
            if (checkPolitica.isChecked) add("Política")
        }.joinToString(", ")

        val usuario = Usuario(
            nombre = nombre,
            correo = correo,
            genero = genero,
            noticias = noticias,
            provincias = provincias
        )

        registrarUsuarios(usuario)
    }

    private fun inicializarBD() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mi_base_datos"
        ).build()
    }

    private fun registrarUsuarios(usuario: Usuario) {
        lifecycleScope.launch(Dispatchers.IO) {
            db.usuarioDao().insertar(usuario)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@RegisterActivity, "Usuario Registrado", Toast.LENGTH_SHORT).show()
                limpiarForm()
                irHome()
            }
        }
    }

    private fun limpiarForm() {
        etNombre.text.clear()
        etCorreo.text.clear()
        etContraseña.text.clear()
        rgGenero.clearCheck()
        checkDeportes.isChecked = false
        checkPolitica.isChecked = false
        checkSalud.isChecked = false
        checkCultura.isChecked = false
        checkEducacion.isChecked = false
        spnProvincias.setSelection(0)
    }

    private fun limpiardatos() {
        limpiarForm()
        cardResultado.visibility = View.GONE
    }

    private fun irHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
