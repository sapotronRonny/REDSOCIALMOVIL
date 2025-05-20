package com.example.proyecto.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto.model.Usuario
import kotlinx.coroutines.flow.Flow
//definir las operaciones a realizar en la entidad
@Dao
interface UsuarioDao {
    //definir crud
    @Insert
    suspend fun insertar (usuario : Usuario)


    //metodo
    @Query("SELECT * FROM usuarios")
    //devolver los datos usando flou
    fun obtenerUsuarios(): Flow<List<Usuario>>
}