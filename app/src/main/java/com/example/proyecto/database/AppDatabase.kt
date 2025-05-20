package com.example.proyecto.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyecto.dao.UsuarioDao
import com.example.proyecto.model.Usuario
//uno solo para todas las tablas
//definir la bd, entidad y version
//dentro de entities se declaran todas las de dao con coma Usuario::class, xxxxxx
//por cada tabla en model es un dao
//metodo abstracto que permite acceder a la funcionalidades del dao
@Database(entities = [Usuario::class], version = 1, exportSchema = false )
abstract class AppDatabase: RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}
