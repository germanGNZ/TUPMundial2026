package com.gonzalez.tupmundial2026.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entidad de Room — representa la tabla "tickets" en la base de datos local.
// Guarda todos los datos del partido al momento de la compra para poder
// mostrarlos aunque el partido ya no esté en la API.

@Entity(tableName = "tickets")
data class Ticket(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int,        // id del usuario que compró (de la tabla usuarios)
    val partidoId: Int,        // id del partido en MockAPI
    val equipo1: String,
    val equipo2: String,
    val fecha: String,
    val estadio: String,
    val grupo: String,
    val precio: String,
    val cantidadEntradas: Int,
    val fechaCompra: Long = System.currentTimeMillis() // timestamp de la compra
)