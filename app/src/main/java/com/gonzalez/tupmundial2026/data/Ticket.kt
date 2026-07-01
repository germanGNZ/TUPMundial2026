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
    val precio: Double,        // precio unitario original, tal como viene de la API
    val cantidadEntradas: Int,
    // sector elegido dentro del estadio (Popular / Platea / VIP)
    val sector: String = "Platea",
    // método de pago elegido (simulado, no se procesa pago real)
    val metodoPago: String = "",
    // datos personales del comprador, obligatorios para confirmar la compra
    val dniComprador: String = "",
    val nombreComprador: String = "",
    val emailComprador: String = "",
    val telefonoComprador: String = "",
    // desglose del precio final pagado
    val subtotal: Double = 0.0,
    val cargoServicio: Double = 0.0,
    val total: Double = 0.0,
    val fechaCompra: Long = System.currentTimeMillis() // timestamp de la compra
)