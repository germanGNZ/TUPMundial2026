package com.gonzalez.tupmundial2026.network

import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.models.DTOPartidosLista
import retrofit2.http.GET
import retrofit2.http.Path

interface MundialApiService {
    @GET("PartidoLista")
    suspend fun getPartidosLista(): List<DTOPartidosLista>

    @GET("PartidosDetalle/{id}")
    suspend fun getPartidoDetalle(
        @Path("id") id: Int
    ): DTOPartidosDetalle
}

