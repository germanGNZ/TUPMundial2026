package com.gonzalez.tupmundial2026.models

import kotlinx.serialization.Serializable

@Serializable
data class DTOPartidosRespuesta(
    val pagina: Int,
    val porPagina: Int,
    val total: Long,
    val totalPaginas: Int,
    val datos: List<DTOPartidosLista>
)