package com.gonzalez.tupmundial2026.models

import kotlinx.serialization.Serializable

@Serializable
data class DTOPartidosLista(
        val id: Int,
        val equipo1: String,
        val equipo2: String,
        val grupo: String,
        val fecha: String,
        val estadio: String,
)
