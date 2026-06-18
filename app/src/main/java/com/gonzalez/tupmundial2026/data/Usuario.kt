package com.gonzalez.tupmundial2026.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreUsuario: String,
    val email: String,
    val passwordHash: String   // guardamos el hash, nunca la contraseña en texto plano
)