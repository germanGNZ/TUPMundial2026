package com.gonzalez.tupmundial2026.repository

import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.models.DTOPartidosLista
import com.gonzalez.tupmundial2026.network.MundialApiService

class MundialRepository(private val api: MundialApiService) {

    suspend fun fetchPartidosLista(): List<DTOPartidosLista> {
        return api.getPartidosLista()
    }
    suspend fun fetchPartidosDetalle(id: Int): DTOPartidosDetalle {
        return api.getPartidosDetalle().first { it.id == id }
    }
}
