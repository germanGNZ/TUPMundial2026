package com.gonzalez.tupmundial2026.repository

import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.models.DTOPartidosLista
import com.gonzalez.tupmundial2026.models.DTOPartidosRespuesta
import com.gonzalez.tupmundial2026.network.MundialApiService

class MundialRepository(private val api: MundialApiService) {

    // Nota: pide una página a la vez, con el tamaño indicado.
    // Nota: La respuesta incluye totalPaginas para saber si hay más páginas.
    suspend fun fetchPartidosPaginados(
        pagina: Int = 1,
        porPagina: Int = 10
    ): DTOPartidosRespuesta {
        return api.getPartidosLista(pagina = pagina, porPagina = porPagina)
    }

    suspend fun fetchPartidoDetalle(id: Int): DTOPartidosDetalle {
        return api.getPartidoDetalle(id)
    }
}

