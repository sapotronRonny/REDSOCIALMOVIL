//package com.example.proyecto
//
//import android.os.Bundle
//import android.widget.ArrayAdapter
//import android.widget.ListView
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.lifecycle.lifecycleScope
//import androidx.room.Room
//import com.example.proyecto.database.AppDatabase
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class RegistrosActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.activity_registros)
//
//        val listView = findViewById<ListView>(R.id.lvUsuarios)
//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "mi_base_datos"
//        ).build()
////obteniendo los usuarios y los observo
//        lifecycleScope.launch {
//            db.usuarioDao().obtenerUsuarios().collect{ usuarios ->
//                val lista = usuarios.map {
//                    "${it.nombre} - ${it.correo}\n${it.genero} | ${it.noticias} | ${it.provincias}"
//                }
//                withContext(Dispatchers.Main){
//                    val adapter = ArrayAdapter(this@RegistrosActivity, android.R.layout.simple_list_item_1, lista)
//                    listView.adapter = adapter
//                }
//            }
//        }
//    }
//}