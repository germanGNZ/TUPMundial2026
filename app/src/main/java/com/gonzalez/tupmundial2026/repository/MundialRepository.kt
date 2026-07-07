package com.gonzalez.tupmundial2026.repository

import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.models.DTOPartidosLista
import com.gonzalez.tupmundial2026.network.MundialApiService

class MundialRepository(private val api: MundialApiService) {

    suspend fun fetchPartidosLista(): List<DTOPartidosLista> {
        // porPagina=104 trae todos los partidos del Mundial en una sola llamada
        return api.getPartidosLista(pagina = 1, porPagina = 104).datos
    }

    suspend fun fetchPartidoDetalle(id: Int): DTOPartidosDetalle {
        return api.getPartidoDetalle(id)
    }
}

