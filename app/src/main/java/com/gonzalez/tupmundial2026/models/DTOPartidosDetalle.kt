package com.gonzalez.tupmundial2026.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DTOPartidosDetalle(
    @SerialName("numeroPartido")
    val id: Int,
    val equipo1: String,
    val equipo2: String,
    val fecha: String,
    val grupo: String? = null,
    val precio: Double = 0.0,
    val estadio: String,
)


