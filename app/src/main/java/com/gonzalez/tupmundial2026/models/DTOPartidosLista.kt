package com.gonzalez.tupmundial2026.models

import kotlinx.serialization.Serializable

@Serializable
data class DTOPartidosLista(
        val id: Int,
        val equipo1: String,
        val equipo2: String,
        val fecha: String,
        val estadio: String,
        val flags1: String,
        val flags2: String
)
