package com.gonzalez.tupmundial2026.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DTOPartidosLista(
        @SerialName("numeroPartido")
        val id: Int,
        val equipo1: String,
        val equipo2: String,
        val grupo: String? = null,
        val fase: String? = null,
        val fecha: String,
        val estadio: String,
)
