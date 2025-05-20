package com.example.proyecto.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios") //definimos el nombre de la entidad
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // creo el campo id como pk
    val nombre: String,
    val correo: String,
    val genero: String,
    val noticias: String,
    val provincias: String
)
