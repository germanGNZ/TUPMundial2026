package com.gonzalez.tupmundial2026.models

import kotlinx.serialization.Serializable

@Serializable
data class DTOTicketRequest(
    val partidoId: String,           // _id de MongoDB del partido
    val numeroPartido: Int,          // número del partido (1-104)
    val equipo1: String,
    val equipo2: String,
    val fechaPartido: String,
    val estadio: String,
    val grupo: String,
    val nombreUsuario: String,
    val emailComprador: String,
    val dniComprador: String,
    val telefonoComprador: String,
    val sector: String,
    val cantidadEntradas: Int,
    val metodoPago: String,
    val precio: Double,
    val subtotal: Double,
    val cargoServicio: Double,
    val total: Double
)

@Serializable
data class DTOTicketResponse(
    val id: String? = null,
    val partidoId: String,
    val numeroPartido: Int,
    val emailComprador: String,
    val sector: String,
    val cantidadEntradas: Int,
    val total: Double,
    val fechaCompra: String? = null
)
