package com.gonzalez.tupmundial2026.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Entidad de Room — representa la tabla "usuarios" en la base de datos local.
// El Index en email garantiza que no se puedan registrar dos usuarios
// con el mismo email.

@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["email"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val email: String,
    val password: String
)